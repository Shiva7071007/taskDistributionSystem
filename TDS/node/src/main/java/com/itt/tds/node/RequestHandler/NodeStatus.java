package com.itt.tds.node.RequestHandler;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.node.LocalNodeState;
import com.itt.tds.utility.Utility;

public class NodeStatus {

	private static final String SUCCESS = "SUCCESS";
	private static final String NODE_STATE = "nodeState";

	public static TDSResponse getNodeStatus(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromrequest(request);
		
		response.setStatus(SUCCESS);
		response.setValue(NODE_STATE, String.valueOf(LocalNodeState.currentNodeState));
		return response;
	}
}
