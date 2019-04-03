package com.itt.tds.coordinator;

import java.io.File;
import java.util.*;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.TDSExceptions.UnableToReadFileException;
import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;
import com.itt.tds.core.Node;

/**
 * 
 */
public class TaskScheduler implements Runnable {
	private static final String EXECUTE_TASK = "executeTask";
	private static final String TASK_ID = "taskId";
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String SUCCESS = "SUCCESS";
	
	static Logger logger = new TDSLogger().getLogger();

    public TaskScheduler() {
    }

    private TDSResponse sendTask(Task task, Node node) throws UnableToReadFileException, ServerCommunicationException {
		TDSRequest request = new CoOrdinator().prepareRequest();
		request.setDestIp(node.getiP());
		request.setDestPort(node.getPort());
		request.setMethod(EXECUTE_TASK);
		request.setParameters(TASK_ID, String.valueOf(task.getId()));
		request.setParameters(TASK_NAME, task.getTaskName());
		request.setParameters(PARAMETERS, task.getTaskParameters().toString());
		request.setData(Utility.convertFileToByte(new File(task.getTaskExePath())));
		
		return TDSClient.getResponse(request, 60);
	}

    public void scheduleTask(List<Task> taskList, List<Node> nodeList) {
        ListIterator<Task> taskListIterator = taskList.listIterator();
        ListIterator<Node> nodeListIterator = nodeList.listIterator();
        
        while(taskListIterator.hasNext() && nodeListIterator.hasNext()) {
        	Task task = taskListIterator.next();
        	Node node = nodeListIterator.next();
        	
        	try {
        		TDSResponse response = sendTask(task, node);
        		if(response.getStatus().equalsIgnoreCase(SUCCESS)) changeTaskStatusAsExecuting(task, node);
        	} catch (UnableToReadFileException | ServerCommunicationException | DatabaseTransactionException e) {
        		logger.error("Exception occured while sending task to node for execution", e);
        	}
        }
    }

	private void changeTaskStatusAsExecuting(Task task, Node node) throws DatabaseTransactionException {
		logger.info("updating task " + task.getId() + " as in_progress and assigned node ID as " + node.getId());
		TDSTaskRepository taskRepo = new TDSTaskRepository();
		task.setTaskState(TaskState.IN_PROGRESS);
		task.setAssignedNodeId(node.getId());
		taskRepo.Modify(task);
	}

	private List<Task> fetchUnscheduledTask() throws DatabaseTransactionException {
        TDSTaskRepository taskRepo = new TDSTaskRepository();
        List <Task> tasklist = taskRepo.GetTasksByStatus(TaskState.PENDING);
        return tasklist;
    }
    
    private List<Node> fetchAvailableNodes() throws DatabaseTransactionException {
    	TDSNodeRepository nodeRepo = new TDSNodeRepository();
        List <Node> nodelist = nodeRepo.GetAailableNodes();
        return nodelist;
    }

	@Override
	public void run() {
		while (true) {
			Utility.sleep(60);
			logger.info("Scheduling tasks....");
			try {
				List <Task> tasklist = fetchUnscheduledTask();
				List <Node> nodeList = fetchAvailableNodes();
				scheduleTask(tasklist, nodeList);
			} catch (DatabaseTransactionException e) {
				logger.error("Exception happened while running task scheduler", e);
			}
		}
	}
}