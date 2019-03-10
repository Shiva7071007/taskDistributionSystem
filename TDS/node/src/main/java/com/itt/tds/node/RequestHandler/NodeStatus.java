package com.itt.tds.node.RequestHandler;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.NodeState;
import com.itt.tds.node.LocalNodeState;
import com.itt.tds.utility.Utility;

public class NodeStatus {

	public static TDSResponse getNodeStatus(TDSRequest request) {
		TDSResponse response = Utility.prepareResponse(request);
		
		response.setStatus("SUCCESS");
		response.setValue("nodeState", String.valueOf(LocalNodeState.currentNodeState));
		return response;
	}
}
