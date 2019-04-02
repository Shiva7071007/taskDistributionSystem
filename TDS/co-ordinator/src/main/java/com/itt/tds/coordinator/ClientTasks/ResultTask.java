package com.itt.tds.coordinator.ClientTasks;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.CoOrdinator;
import com.itt.tds.coordinator.db.repository.TDSTaskResultRepository;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskResult;
import com.itt.tds.core.TaskState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class ResultTask {

	private static final String TASK_ID = "taskId";
	private static final String SUCCESS = "SUCCESS";
	private static final String TASK_RESULT = "taskResult";
	private static final String TASK_OUTCOME = "taskOutcome";
	private static final String TASK_ERROR_CODE = "taskErrorCode";
	private static final String TASK_ERROR_MESSAGE = "taskErrorMessage";
	
	static Logger logger = new TDSLogger().getLogger();

	public static TDSResponse getResult(TDSRequest request) {
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

			if (task.getTaskState() != TaskState.COMPLETED)
				return coOrdinator.getTaskNotExecutedResponse(request);

			TDSTaskResultRepository tdsTaskResultRepo = new TDSTaskResultRepository();
			TaskResult taskResult = tdsTaskResultRepo
					.getTaskResultByTaskId(Integer.parseInt(request.getParameters(TASK_ID)));

			response = Utility.prepareResponseFromRequest(request);
			response.setStatus(SUCCESS);
			response.setValue(TASK_OUTCOME, String.valueOf(taskResult.getTaskOutcome()));
			response.setValue(TASK_ERROR_CODE, String.valueOf(taskResult.getErrorCode()));
			response.setValue(TASK_ERROR_MESSAGE, taskResult.getErrorMessage());
			response.setValue(TASK_RESULT, Utility.byteToString(taskResult.getResultBuffer()));

		} catch (DatabaseTransactionException e) {
			logger.error("Error while processing result request for client", e);
			response = coOrdinator.getUnableToPerformResponse(request);
		}

		return response;
	}

}
