package com.itt.tds.coordinator.ClientTasks;

import java.util.List;
import java.util.ListIterator;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.db.repository.TDSClientRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.utility.Utility;

public class QueryTask {

	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String TASK_ID = "taskId";
	private static final String TASK_STATUS = "taskStatus";

	public static TDSResponse getTaskStatus(TDSRequest request) {

		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		TDSResponse response = Utility.prepareResponse(request);

		Client client = null;
		TDSClientRepository clientRepository = new TDSClientRepository();
		List<Client> clientList;
		try {
			clientList = clientRepository.GetClients();

			ListIterator<Client> clientListIterator = clientList.listIterator();
			while (clientListIterator.hasNext()) {
				Client tempClient = clientListIterator.next();
				if (tempClient.getHostName().equalsIgnoreCase(request.getParameters(HOSTNAME))) {
					if (tempClient.getUserName().equalsIgnoreCase(request.getParameters(USERNAME))) {
						client = tempClient;
						break;
					}
				}
			}

			if (client == null) {
				response.setStatus("ERROR");
				response.setErrorCode("404");
				response.setErrorMessage("no such client is found");
			} else {
				Task task = null;
				List<Task> clientTaskList = new TDSTaskRepository().GetTasksByClientId(client.getId());

				ListIterator<Task> clientTaskListIterator = clientTaskList.listIterator();
				while (clientTaskListIterator.hasNext()) {
					Task tempTask = clientTaskListIterator.next();
					if (tempTask.getId() == Integer.parseInt(request.getParameters(TASK_ID))) {
						task = tempTask;
					}
				}
				if (task == null) {
					response.setStatus("ERROR");
					response.setErrorCode("405");
					response.setErrorMessage("no such task is found for client");
				} else {
					response.setStatus("SUCCESS");
					response.setValue(TASK_STATUS, String.valueOf(task.getTaskState()));
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
