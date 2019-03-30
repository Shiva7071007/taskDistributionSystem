package com.itt.tds.coordinator;

import java.util.*;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.db.repository.TDSClientRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.core.Client;
import com.itt.tds.core.Task;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.utility.Utility;

/**
 * 
 */
public class CoOrdinator {

	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String ERROR = "ERROR";

	public Client getClientFromRequest(TDSRequest request) throws DatabaseTransactionException {
		Client client = null;
		TDSClientRepository clientRepository = new TDSClientRepository();
		List<Client> clientList = clientRepository.GetClients();

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
		
		return client;
	}

	public Task getTaskForClient(int clientId, int taskId) throws DatabaseTransactionException {
		Task task = null;
		List<Task> clientTaskList = new TDSTaskRepository().GetTasksByClientId(clientId);

		ListIterator<Task> clientTaskListIterator = clientTaskList.listIterator();
		while (clientTaskListIterator.hasNext()) {
			Task tempTask = clientTaskListIterator.next();
			if (tempTask.getId() == taskId) {
				task = tempTask;
			}
		}
		return task;
	}
	

	public TDSResponse getInvalidClientResponse(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromRequest(request);
		response.setStatus(ERROR);
		response.setErrorCode(String.valueOf(TDSError.CLIENT_NOT_REGISTERED.getCode()));
		response.setErrorMessage(TDSError.CLIENT_NOT_REGISTERED.getDescription());
		return response;
	}

	public TDSResponse getNoSuchTaskResponse(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromRequest(request);
		response.setStatus(ERROR);
		response.setErrorCode(String.valueOf(TDSError.INVALID_TASK_ID.getCode()));
		response.setErrorMessage(TDSError.INVALID_TASK_ID.getDescription());
		return response;
	}

	public TDSResponse getUnableToPerformResponse(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromRequest(request);
		response.setStatus(ERROR);
		response.setErrorCode(String.valueOf(TDSError.UNABLE_TO_PROCESS_REQUEST.getCode()));
		response.setErrorMessage(TDSError.UNABLE_TO_PROCESS_REQUEST.getDescription());
		return response;
	}

	public TDSResponse getTaskNotExecutedResponse(TDSRequest request) {
		TDSResponse response = Utility.prepareResponseFromRequest(request);
		response.setStatus(ERROR);
		response.setErrorCode(String.valueOf(TDSError.TASK_NOT_EXECUTED.getCode()));
		response.setErrorMessage(TDSError.TASK_NOT_EXECUTED.getDescription());
		return response;
	}
}