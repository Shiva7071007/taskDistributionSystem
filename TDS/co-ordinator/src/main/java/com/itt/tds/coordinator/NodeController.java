package com.itt.tds.coordinator;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.NodeTasks.NodeAdd;
import com.itt.tds.coordinator.NodeTasks.SaveResult;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class NodeController implements TDSController {
	private static final Object NODE_ADD = "node-add";
	private static final String NODE_POST_RESULT = "node-postResult";
	private static final String ERROR = "ERROR";
	static Logger logger = new TDSLogger().getLogger();

	/**
	 * Default constructor
	 */
	public NodeController() {
	}

	@Override
	public TDSResponse processRequest(TDSRequest request) {
		TDSResponse response = null;
		
		if (request.getMethod().equals(NODE_ADD))
			response = NodeAdd.addNode(request);
		if (request.getMethod().equals(NODE_POST_RESULT))
			response = SaveResult.addTaskResult(request);
		
		if(response == null) {
			response = Utility.prepareResponseFromRequest(request);
			response.setStatus(ERROR);
			response.setErrorCode(String.valueOf(TDSError.INVALID_REQUEST_METHOD.getCode()));
			response.setErrorMessage(TDSError.INVALID_REQUEST_METHOD.getDescription());
		}
		
		return response;
	}
}