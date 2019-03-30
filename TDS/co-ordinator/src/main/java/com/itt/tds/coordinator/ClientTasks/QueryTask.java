package com.itt.tds.coordinator.ClientTasks;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.CoOrdinator;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class QueryTask {
	
	static Logger logger = new TDSLogger().getLogger();

	private static final String TASK_ID = "taskId";
	private static final String TASK_STATUS = "taskStatus";
	private static final String SUCCESS = "SUCCESS";

	public static TDSResponse getTaskStatus(TDSRequest request) {

		TDSResponse response = null;
		CoOrdinator coOrdinator = new CoOrdinator();

		try {
			Client client = coOrdinator.getClientFromRequest(request);

			if (client == null)
				return coOrdinator.getInvalidClientResponse(request);
			
			int taskId = Integer.parseInt(request.getParameters(TASK_ID));
			Task task = coOrdinator.getTaskForClient(client.getId(), taskId);

			if (task == null)
				return coOrdinator.getNoSuchTaskResponse(request);

			response = Utility.prepareResponseFromRequest(request);
			response.setStatus(SUCCESS);
			response.setValue(TASK_STATUS, String.valueOf(task.getTaskState()));

		} catch (DatabaseTransactionException e) {
			logger.error("Error while processing query request for client", e);
			response = coOrdinator.getUnableToPerformResponse(request);
		}
		return response;
	}
}
