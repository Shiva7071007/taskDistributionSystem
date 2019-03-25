package com.itt.tds.node;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.node.RequestHandler.NodeStatus;
import com.itt.tds.node.RequestHandler.TaskExecuter;
import com.itt.tds.utility.Utility;

public class Controller {

	private static final String GET_STATUS = "getStatus";
	private static final String EXECUTE_TASK = "executeTask";
	private static final String ERROR = "ERROR";

	public static TDSResponse processRequest(TDSRequest request) {
		if (request.getMethod().equalsIgnoreCase(EXECUTE_TASK))
			return TaskExecuter.executeTask(request);
		if (request.getMethod().equalsIgnoreCase(GET_STATUS))
			return NodeStatus.getNodeStatus(request);
		
		return invalidMethod(request);
	}

	private static TDSResponse invalidMethod(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromrequest(request);
		response.setStatus(ERROR);
		response.setErrorCode(String.valueOf(TDSError.INVALID_REQUEST_METHOD.getCode()));
		response.setErrorMessage(TDSError.INVALID_REQUEST_METHOD.getDescription());
		return response;
	}

}
