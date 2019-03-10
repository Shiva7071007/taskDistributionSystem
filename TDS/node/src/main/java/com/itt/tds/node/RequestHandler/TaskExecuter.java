package com.itt.tds.node.RequestHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.NodeState;
import com.itt.tds.core.Task;
import com.itt.tds.node.LocalNodeState;
import com.itt.tds.utility.Utility;

public class TaskExecuter {
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String TASK_FOLDER = "tasks\\";
	private static final String SPACE = " ";

	public static TDSResponse executeTask(TDSRequest request) {
		LocalNodeState.currentNodeState = NodeState.BUSY;
		TDSResponse response = Utility.prepareResponse(request);
		try {
			Task task = new Task();
			task.setTaskName(request.getParameters(TASK_NAME));
			String taskParameters = request.getParameters(PARAMETERS).substring(1,
					request.getParameters(PARAMETERS).length() - 1);
			task.setTaskParameters(new ArrayList<>(Arrays.asList(taskParameters.split(","))));

			File dir = new File(TASK_FOLDER);
			dir.mkdirs();

			String taskAddress = dir.getAbsolutePath() + "\\" + task.getTaskName();

			FileOutputStream fileStream = new FileOutputStream(taskAddress);
			fileStream.write(request.getData());
			fileStream.close();
			task.setTaskExePath(taskAddress);

			String executableCommand = task.getTaskExePath();

			Iterator<String> taskParametersIterator = task.getTaskParameters().iterator();
			while (taskParametersIterator.hasNext()) {
				executableCommand += SPACE;
				executableCommand += taskParametersIterator.next();
			}

			Process proc = Runtime.getRuntime().exec(executableCommand);

			BufferedReader bri = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader bre = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String line = null;
			int taskOutcome = 0;
			String taskResult = null;
			String taskErrMsg = null;
			int taskErrCode = 1;

			while ((line = bri.readLine()) != null) {
				taskResult += line;
			}
			bri.close();

			while ((line = bre.readLine()) != null) {
				taskErrMsg += line;
			}
			bre.close();

			taskErrCode = proc.waitFor();
			taskOutcome = (taskErrCode) == 0 ? 1 : 2;

			response.setStatus("SUCCESS");
			response.setValue("taskOutcome", String.valueOf(taskOutcome));
			response.setValue("taskErrCode", String.valueOf(taskErrCode));
			response.setValue("taskErrMsg", taskErrMsg);
			response.setData(Utility.stringToByteArray(taskResult));
		} catch (Exception e) {
			response.setStatus("FAILURE");
		} finally {
			LocalNodeState.currentNodeState = NodeState.AVAILABLE;
		}
		return response;
	}

}
