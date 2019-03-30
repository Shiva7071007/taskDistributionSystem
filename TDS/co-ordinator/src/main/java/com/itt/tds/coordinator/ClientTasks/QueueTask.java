package com.itt.tds.coordinator.ClientTasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.CoOrdinator;
import com.itt.tds.coordinator.db.repository.TDSClientRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class QueueTask {
	static Logger logger = new TDSLogger().getLogger();
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String TASK_FOLDER = "tasks\\";
	private static final String TASK_STATUS = "taskStatus";
	private static final String TASK_ID = "taskId";
	private static final String SUCCESS = "SUCCESS";
	private static final String QUEUED = "queued";

	public static TDSResponse addTask(TDSRequest request) {

		TDSResponse response = null;
		CoOrdinator coOrdinator = new CoOrdinator();
		TDSClientRepository clientRepository = new TDSClientRepository();

		try {
			Client client = coOrdinator.getClientFromRequest(request);

			if (client == null) {
				client = new Client();
				client.setHostName(request.getParameters(HOSTNAME));
				client.setUserName(request.getParameters(USERNAME));
				client.setId(clientRepository.Add(client));
			}

			int clientID = client.getId();

			Task task = new Task();
			task.setTaskName(request.getParameters(TASK_NAME));

			String taskParameters = request.getParameters(PARAMETERS).substring(1,
					request.getParameters(PARAMETERS).length() - 1);
			task.setTaskParameters(new ArrayList<>(Arrays.asList(taskParameters.split(","))));

			task.setUserId(clientID);

			File dir = new File(TASK_FOLDER + clientID);
			dir.mkdirs();
			logger.info("created directory for tasks");

			String taskAddress = dir.getAbsolutePath() + "\\" + task.getTaskName();
			FileOutputStream fileStream = new FileOutputStream(taskAddress);
			fileStream.write(request.getData());
			fileStream.close();
			task.setTaskExePath(taskAddress);

			task.setTaskState(TaskState.PENDING);

			TDSTaskRepository taskRepository = new TDSTaskRepository();
			int taskID = taskRepository.Add(task);
			logger.trace("task id got ==> " + taskID);

			response = Utility.prepareResponseFromRequest(request);
			response.setStatus(SUCCESS);
			response.setValue(TASK_STATUS, QUEUED);
			response.setValue(TASK_ID, String.valueOf(taskID));

		} catch (DatabaseTransactionException | IOException e) {
			logger.error("Error while processing queue request for client", e);
			response = coOrdinator.getUnableToPerformResponse(request);
		}
		return response;
	}

}
