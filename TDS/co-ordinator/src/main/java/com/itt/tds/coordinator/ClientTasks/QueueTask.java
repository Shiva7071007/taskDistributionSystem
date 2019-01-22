package com.itt.tds.coordinator.ClientTasks;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.db.repository.TDSClientRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskState;
import com.itt.tds.logging.TDSLogger;

public class QueueTask {
	static Logger logger = new TDSLogger().getLogger();
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String TASK_FOLDER = "tasks\\";

	public static TDSResponse addTask(TDSRequest request) {
		Client client = new Client();
		client.setHostName(request.getParameters(HOSTNAME));
		client.setUserName(request.getParameters(USERNAME));

		TDSClientRepository clientRepository = new TDSClientRepository();
		int clientID;
		try {
			clientID = clientRepository.Add(client);

			Task task = new Task();
			task.setTaskName(request.getParameters(TASK_NAME));

			String taskParameters = request.getParameters(PARAMETERS).substring(1,
					request.getParameters(PARAMETERS).length() - 1);
			task.setTaskParameters((ArrayList<String>) Arrays.asList(taskParameters.split(",")));

			task.setUserId(clientID);

			String taskAddress = TASK_FOLDER + TASK_NAME;
			FileOutputStream fileStream = new FileOutputStream(taskAddress);
			fileStream.write(request.getData());
			task.setTaskExePath(taskAddress);
			
			task.setTaskState(TaskState.PENDING);
			task.setAssignedNodeId(0);
			
			TDSTaskRepository taskRepository = new TDSTaskRepository();
			int taskID = taskRepository.Add(task);
			
			TDSResponse response = new TDSResponse();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
