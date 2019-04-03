package com.itt.tds.node.RequestHandler;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.node.LocalNodeState;
import com.itt.tds.utility.Utility;

public class NodeStatus {

	private static final String SUCCESS = "SUCCESS";
	private static final String NODE_STATE = "nodeState";
	
	static Logger logger = new TDSLogger().getLogger();

	public static TDSResponse getNodeStatus(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromRequest(request);
		
		logger.info("Sending Node Status as " + LocalNodeState.currentNodeState);
		
		response.setStatus(SUCCESS);
		response.setValue(NODE_STATE, String.valueOf(LocalNodeState.currentNodeState));
		return response;
	}
}
