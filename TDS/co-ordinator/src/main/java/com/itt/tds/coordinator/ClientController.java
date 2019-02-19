package com.itt.tds.coordinator;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.ClientTasks.QueryTask;
import com.itt.tds.coordinator.ClientTasks.QueueTask;
import com.itt.tds.coordinator.ClientTasks.ResultTask;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class ClientController implements TDSController {
	static Logger logger = new TDSLogger().getLogger();
	private static final String CLIENT_QUERY_TASK = "client-queryTask";
	private static final String CLIENT_RESULT_TASK = "client-resultTask";
	private static final String CLIENT_QUEUE_TASK = "client-queueTask";
	private static final String ERROR = "ERROR";

	/**
	 * Default constructor
	 */
	public ClientController() {
	}

	@Override
	public TDSResponse processRequest(TDSRequest request) {
		logger.debug("processing request");
		TDSResponse response = null;

		if (request.getMethod().equals(CLIENT_QUEUE_TASK))
			response = QueueTask.addTask(request);
		else if (request.getMethod().equals(CLIENT_QUERY_TASK))
			response = QueryTask.getTaskStatus(request);
		else if (request.getMethod().equals(CLIENT_RESULT_TASK))
			response = ResultTask.getResult(request);
		else {
			response = new TDSResponse();
			response.setProtocolVersion(request.getProtocolVersion());
			response.setProtocolFormat(request.getProtocolFormat());
			response.setDestIp(request.getSourceIp());
			response.setDestPort(request.getSourcePort());
			response.setSourceIp(request.getDestIp());
			response.setSourcePort(request.getDestPort());
			response.setStatus(ERROR);
			response.setErrorCode(String.valueOf(TDSError.INVALID_REQUEST_METHOD.getCode()));
			response.setErrorMessage(TDSError.INVALID_REQUEST_METHOD.getDescription());
		}
		return response;
	}
}