package com.itt.tds.node.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.itt.tds.core.Task;
import com.itt.tds.core.TaskOutcome;
import com.itt.tds.core.TaskResult;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.node.Node;
import com.itt.tds.utility.Utility;

public class TaskExecuter implements Runnable {
	private static final String SPACE = " ";

	static Logger logger = new TDSLogger().getLogger();

	Task task;

	public TaskExecuter(Task task) {
		this.task = task;
	}

	@Override
	public void run() {
		TaskResult taskResult = new TaskResult();
		taskResult.setTaskId(task.getId());
		try {
			String executableCommand = task.getTaskExePath();

			Iterator<String> taskParametersIterator = task.getTaskParameters().iterator();
			while (taskParametersIterator.hasNext()) {
				executableCommand += SPACE;
				executableCommand += taskParametersIterator.next();
			}

			Process proc = Runtime.getRuntime().exec(executableCommand);

			BufferedReader inputStream = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String line = "";
			String result = "";
			String taskErrMsg = "";
			int taskErrCode = 1;

			while ((line = inputStream.readLine()) != null) {
				result = line;
			}
			inputStream.close();

			while ((line = errorStream.readLine()) != null) {
				taskErrMsg += line;
			}
			errorStream.close();

			taskErrCode = proc.waitFor();
			
			logger.info("task result error code ==>" + taskErrCode);
			logger.info("task result error msg ==>" + taskErrMsg);
			logger.info("task result output ==>" + result);
			

			taskResult.setTaskOutcome((taskErrCode) == 0 ? 1 : 2);
			taskResult.setErrorCode(taskErrCode);
			taskResult.setErrorMessage(taskErrMsg);
			taskResult.setResultBuffer(Utility.stringToByteArray(result));
			
			Utility.deleteFile(task.getTaskExePath());	

		} catch (IOException | InterruptedException io) {

			logger.error("error while executing task", io);

			taskResult.setTaskOutcome(TaskOutcome.FAILED);
			taskResult.setErrorCode(TDSError.FAILED_TO_PROCESS_TASK.getCode());
			taskResult.setErrorMessage(TDSError.FAILED_TO_PROCESS_TASK.getDescription());
		}
		
		new Node().postResult(taskResult);
	}
}
