package com.itt.tds.client;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.utility.Utility;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "query", mixinStandardHelpOptions = true, header = "get the current status of task by task ID")
public class Query extends Client implements Runnable {

	@Parameters(index = "0", description = "task-id of the task got from server while queuing")
	int taskId;

	private static final String CLIENT_QUERY_TASK = "client-queryTask";
	private static final String TASK_STATUS = "taskStatus";
	private static final String STATUS = "Status";

	@Override
	public void run() {
		Client.setClientLogLevel();

		TDSRequest request = prepareClientRequest();
		request.setMethod(CLIENT_QUERY_TASK);
		request.setParameters(TASK_ID, Integer.toString(taskId));

		TDSResponse response = getResponse(request, TIMEOUT);

		if (response.getStatus().equalsIgnoreCase(SUCCESS)) {
			String statusCode = response.getValue(TASK_STATUS);
			logger.info(STATUS + SEPARATOR + getStatusValueFromCode(statusCode));
		} else {
			Utility.displayErrorMsg(response);
		}
	}
}
