package com.itt.tds.coordinator.NodeTasks;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.Node;
import com.itt.tds.core.NodeState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;
import com.itt.tds.coordinator.CoOrdinator;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;

public class NodeAdd {
	static Logger logger = new TDSLogger().getLogger();
	private static final String NODE_STATE = "nodeState";
	private static final String NODE_ID = "nodeId";
	private static final String SUCCESS = "SUCCESS";

	public static TDSResponse addNode(TDSRequest request) {

		TDSResponse response = null;
		TDSNodeRepository nodeRepo = new TDSNodeRepository();
		CoOrdinator coOrdinator = new CoOrdinator();

		try {
			Node node = coOrdinator.getNodeFromRequest(request);

			if (node == null) {
				node = new Node();
				node.setiP(request.getSourceIp());
				node.setPort(request.getSourcePort());
				node.setStatus(Integer.parseInt(request.getParameters(NODE_STATE)));
				node.setId(nodeRepo.Add(node));
			}
			
			node.setStatus(NodeState.AVAILABLE);
			nodeRepo.Modify(node);

			response = Utility.prepareResponseFromRequest(request);
			response.setStatus(SUCCESS);
			response.setValue(NODE_ID, String.valueOf(node.getId()));

		} catch (DatabaseTransactionException e) {
			logger.error("Error while processing add request for node", e);
			response = coOrdinator.getUnableToPerformResponse(request);
		}
		return response;
	}
}
