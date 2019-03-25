package com.itt.tds.node.RequestHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.NodeState;
import com.itt.tds.core.Task;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.node.LocalNodeState;
import com.itt.tds.utility.Utility;

public class TaskReciever {
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String TASK_FOLDER = "tasks\\";
	private static final String TASK_ID = "taskId";
	private static final String ERROR = "ERROR";
	
	static Logger logger = new TDSLogger().getLogger();

	public static TDSResponse recieveTask(TDSRequest request) {
		LocalNodeState.currentNodeState = NodeState.BUSY;
		TDSResponse response = Utility.prepareResponseFromrequest(request);
		Task task = new Task();

		task.setId(Integer.valueOf(request.getParameters(TASK_ID)));

		task.setTaskName(request.getParameters(TASK_NAME));

		String taskParameters = request.getParameters(PARAMETERS).substring(1,
				request.getParameters(PARAMETERS).length() - 1);
		task.setTaskParameters(new ArrayList<>(Arrays.asList(taskParameters.split(","))));

		File dir = new File(TASK_FOLDER);
		dir.mkdirs();

		try {
			String taskAddress = dir.getAbsolutePath() + "\\" + task.getTaskName();
			FileOutputStream fileStream = new FileOutputStream(taskAddress);
			fileStream.write(request.getData());
			fileStream.close();
			task.setTaskExePath(taskAddress);
			
			Thread taskExecuterThread = new Thread(new TaskExecuter(task));
			taskExecuterThread.start();
			
		} catch (IOException io) {
			logger.error("error while executing task", io);
			
			response.setStatus(ERROR);
			response.setErrorCode(String.valueOf(TDSError.FAILED_TO_PROCESS_TASK.getCode()));
			response.setErrorMessage(TDSError.FAILED_TO_PROCESS_TASK.getDescription());

			LocalNodeState.currentNodeState = NodeState.AVAILABLE;
		}
		return response;
	}

}
