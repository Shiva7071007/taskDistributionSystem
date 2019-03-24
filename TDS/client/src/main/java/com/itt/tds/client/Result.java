package com.itt.tds.client;

import static picocli.CommandLine.*;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.utility.Utility;

import picocli.CommandLine.Parameters;

@Command(name = "result", mixinStandardHelpOptions = true, header = "get the result for passed task ID")
public class Result extends Client implements Runnable {
	@Parameters(index = "0", description = "task-id of the task got from server while queuing")
	int taskId;

	private static final String CLIENT_RESULT_TASK = "client-resultTask";
	private static final String TASK_RESULT = "taskResult";
	private static final String TASK_OUTCOME = "taskOutcome";
	private static final String TASK_ERROR_CODE = "taskErrorCode";
	private static final String TASK_ERROR_MESSAGE = "taskErrorMessage";

	@Override
	public void run() {
		Client.setClientLogLevel();

		TDSRequest request = Client.prepareClientRequest();
		request.setMethod(CLIENT_RESULT_TASK);
		request.setParameters(TASK_ID, Integer.toString(taskId));

		TDSResponse response = Client.getResponse(request, TIMEOUT);

		if (response.getStatus().equalsIgnoreCase(SUCCESS)) {
			String taskOutcome = response.getValue(TASK_OUTCOME);
			String taskErrorCode = response.getValue(TASK_ERROR_CODE);
			String taskErrorMessage = response.getValue(TASK_ERROR_MESSAGE);
			String taskResult = response.getValue(TASK_RESULT);
			
			logger.info("Task Outcome" + SEPARATOR + taskOutcome);
			logger.info("Task Error Code" + SEPARATOR + taskErrorCode);
			logger.info("Task Error Message" + SEPARATOR + taskErrorMessage);
			logger.info("Task Result" + SEPARATOR + taskResult);
			
		} else {
			Utility.displayErrorMsg(response);
		}
	}
}
