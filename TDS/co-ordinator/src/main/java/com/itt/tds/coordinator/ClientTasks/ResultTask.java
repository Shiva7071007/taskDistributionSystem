package com.itt.tds.coordinator.ClientTasks;

import java.util.List;
import java.util.ListIterator;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.db.repository.TDSClientRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskResultRepository;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskOutcome;
import com.itt.tds.core.TaskResult;
import com.itt.tds.utility.Utility;

public class ResultTask {
	
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String TASK_ID = "taskId";
	private static final String TASK_STATUS = "taskStatus";

	public static TDSResponse getResult(TDSRequest request) throws Exception {
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		TDSResponse response = new TDSResponse();
		response.setProtocolVersion(tdsCFG.getCoordinatorProtocolVersion());
		response.setProtocolFormat(tdsCFG.getCoordinatorProtocolFormat());
		response.setSourceIp(tdsCFG.getCoordinatorIP());
		response.setSourcePort(tdsCFG.getCoordinatorPort());
		response.setDestIp(request.getSourceIp());
		response.setDestPort(request.getDestPort());
		
		Client client = null;
		TDSClientRepository clientRepository = new TDSClientRepository();
		List<Client> clientList = clientRepository.GetClients();
		
		ListIterator<Client> clientListIterator = clientList.listIterator();
		while(clientListIterator.hasNext())
		{
			Client tempClient = clientListIterator.next();
			if(tempClient.getHostName().equalsIgnoreCase(request.getParameters(HOSTNAME))) {
				if(tempClient.getUserName().equalsIgnoreCase(request.getParameters(USERNAME))) {
					client = tempClient;
					break;
				}
			}
		}
		
		if(client == null) {
			response.setStatus("ERROR");
			response.setErrorCode("404");
			response.setErrorMessage("no such client is found");
		} else {
			Task task = null;
			List<Task> clientTaskList = new TDSTaskRepository().GetTasksByClientId(client.getId());
			
			ListIterator<Task> clientTaskListIterator = clientTaskList.listIterator();
			while(clientTaskListIterator.hasNext()) {
				Task tempTask = clientTaskListIterator.next();
				if(tempTask.getId() == Integer.parseInt(request.getParameters(TASK_ID)))
				{
					task = tempTask;
				}
			}
			if(task == null) {
				response.setStatus("ERROR");
				response.setErrorCode("405");
				response.setErrorMessage("no such task is found for client");
			} else {
				if(task.getTaskState() != 3) {
					response.setStatus("ERROR");
					response.setErrorCode("406");
					response.setErrorMessage("Task not excuted yet");
					response.setValue(TASK_STATUS,String.valueOf(task.getTaskState()));
				} else {
					TDSTaskResultRepository tdsTaskResultRepo = new TDSTaskResultRepository();
					TaskResult taskResult = tdsTaskResultRepo.getTaskResultByTaskId(Integer.parseInt(request.getParameters(TASK_ID)));
					response.setStatus("SUCCESS");
					response.setValue("taskOutcome", String.valueOf(taskResult.getTaskOutcome()));
					response.setValue("taskErrorCode", String.valueOf(taskResult.getErrorCode()));
					response.setValue("taskErrorMessage", taskResult.getErrorMessage());
					response.setValue("taskResult", Utility.byteToString(taskResult.getResultBuffer()));
				}
			}
			
		}
		
		return response;
	}

}
