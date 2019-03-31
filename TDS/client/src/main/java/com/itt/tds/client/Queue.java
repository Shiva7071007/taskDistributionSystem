package com.itt.tds.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.TDSExceptions.UnableToReadFileException;
import com.itt.tds.TDSExceptions.RuntimeExceptions.FatalException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.utility.Utility;

import picocli.CommandLine.*;
import picocli.CommandLine.Parameters;

@Command(name = "queue", mixinStandardHelpOptions = true, header = "add a program in queue for execution")
public class Queue extends Client implements Runnable {

	@Parameters(index = "0", description = "Executables files that needs to be sent to server")
	File task;

	@Parameters(index = "1..*", description = "parameters need to be passed with task")
	List<String> parameters = new ArrayList<String>();

	private static final String CLIENT_QUEUE_TASK = "client-queueTask";
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String TASK_STATUS = "taskStatus";

	@Override
	public void run() {

		Client.setClientLogLevel();

		if (!task.exists()) {
			throw new FatalException("passed task: " + task.getName() + " not found");
		}

		TDSRequest request = Client.prepareClientRequest();
		request.setMethod(CLIENT_QUEUE_TASK);
		request.setParameters(TASK_NAME, task.getName());
		request.setParameters(PARAMETERS, parameters.toString());
		
		try {
			request.setData(Utility.convertFileToByte(task));
		} catch (UnableToReadFileException e) {
			throw new FatalException(TDSError.FAILED_TO_READ_FILE, e);
		}

		TDSResponse response = Client.getResponse(request, TIMEOUT);

		if (response.getStatus().equalsIgnoreCase(SUCCESS)) {
			String status = response.getValue(TASK_STATUS);
			String taskId = response.getValue(TASK_ID);
			logger.info(status + ", task ID:" + taskId);
		} else {
			Utility.displayErrorMsg(response);
		}
	}
}
