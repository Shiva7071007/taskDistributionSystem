package com.itt.tds.coordinator;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.ClientTasks.QueryTask;
import com.itt.tds.coordinator.ClientTasks.QueueTask;
import com.itt.tds.coordinator.ClientTasks.ResultTask;
import com.itt.tds.logging.TDSLogger;

public class ClientController implements TDSController {
	static Logger logger = new TDSLogger().getLogger();
	private static final String CLIENT_QUERY_TASK = "client-queryTask";
	private static final String CLIENT_RESULT_TASK = "client-resultTask";
	private static final String CLIENT_QUEUE_TASK = "client-queueTask";

	/**
	 * Default constructor
	 */
	public ClientController() {
	}
	
	@Override
	public TDSResponse processRequest(TDSRequest request) {
		logger.info("processing request");
		if (request.getMethod().equals(CLIENT_QUEUE_TASK))
			return QueueTask.addTask(request);
		if (request.getMethod().equals(CLIENT_QUERY_TASK))
			return QueryTask.getTaskStatus(request);
		if (request.getMethod().equals(CLIENT_RESULT_TASK))
			return ResultTask.getResult(request);
		return null;
	}
}