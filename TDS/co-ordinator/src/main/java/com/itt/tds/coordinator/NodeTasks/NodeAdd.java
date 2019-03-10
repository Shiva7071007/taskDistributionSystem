package com.itt.tds.coordinator.NodeTasks;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.Node;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;

public class NodeAdd {
	static Logger logger = new TDSLogger().getLogger();
	private static final String NODE_STATE = "nodeState";
	private static final String NODE_ID = "nodeId";

	public static TDSResponse addNode(TDSRequest request) {
		Node node = null;
		TDSNodeRepository nodeRepo = new TDSNodeRepository();
		TDSResponse response = Utility.prepareResponse(request);

		try {
			List<Node> nodeList = nodeRepo.GetAllNodes();

			ListIterator<Node> nodeListIterator = nodeList.listIterator();

			while (nodeListIterator.hasNext()) {
				Node tempNode = nodeListIterator.next();
				if (tempNode.getiP().equalsIgnoreCase(request.getSourceIp())
						&& tempNode.getPort() == request.getSourcePort()) {
					node = tempNode;
					break;
				}
			}

			if (node == null) {
				node = new Node();
				node.setiP(request.getSourceIp());
				node.setPort(request.getSourcePort());
				node.setStatus(Integer.parseInt(request.getParameters(NODE_STATE)));
				node.setId(nodeRepo.Add(node));
			}

			TDSConfiguration tdsCFG = TDSConfiguration.getInstance();

			response.setStatus("SUCCESS");
			response.setErrorCode("0");
			response.setErrorMessage("");
			response.setValue(NODE_ID, String.valueOf(node.getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
