package com.itt.tds.node;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.node.RequestHandler.NodeStatus;
import com.itt.tds.node.RequestHandler.TaskExecuter;

public class Controller {

	private static final Object GET_STATUS = "getStatus";
	private static final Object EXECUTE_TASK = "executeTask";

	public static TDSResponse processRequest(TDSRequest request) {
		try {
			if (request.getMethod().equals(EXECUTE_TASK))
				return TaskExecuter.executeTask(request);
			if (request.getMethod().equals(GET_STATUS))
				return NodeStatus.getNodeStatus(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
