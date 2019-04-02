package com.itt.tds.coordinator;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;
import com.itt.tds.core.Node;
import com.itt.tds.core.NodeState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class NodeHealthMonitor implements Runnable {
	private static final String GET_STATUS = "getStatus";
	private static final String SUCCESS = "SUCCESS";
	private static final String NODE_STATE = "nodeState";

	static Logger logger = new TDSLogger().getLogger();

	public NodeHealthMonitor() {
	}

	private int checkNodeStatus(Node node) {
		TDSRequest request = new CoOrdinator().prepareRequest();
		request.setDestIp(node.getiP());
		request.setDestPort(node.getPort());
		request.setMethod(GET_STATUS);

		int nodeStatus = NodeState.NOT_OPERATIONAL;
		try {
			TDSResponse response = TDSClient.getResponse(request, 10);
			if (response.getStatus() == SUCCESS) {
				nodeStatus = Integer.parseInt(response.getValue(NODE_STATE));
			}
		} catch (ServerCommunicationException e) {
			nodeStatus = NodeState.NOT_OPERATIONAL;
		}

		return nodeStatus;
	}

	@Override
	public void run() {
		while (true) {
			TDSNodeRepository nodeRepo = new TDSNodeRepository();
			try {
				List<Node> allNodeList = nodeRepo.GetAllNodes();
				ListIterator<Node> allNodeListIterator = allNodeList.listIterator();
				
				while (allNodeListIterator.hasNext()) {
					Node node = allNodeListIterator.next();
					int newNodeStatus = checkNodeStatus(node);
					
					if(newNodeStatus != node.getStatus()) {
						node.setStatus(newNodeStatus);
						nodeRepo.Modify(node);						
					}
				}
			} catch (DatabaseTransactionException e) {
				logger.error("exception happened while getting node status", e);
			}
			Utility.sleep(120);
		}
	}
}