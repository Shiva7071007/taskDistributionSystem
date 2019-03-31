package com.itt.tds.coordinator.NodeTasks;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.CoOrdinator;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskResultRepository;
import com.itt.tds.core.Node;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskResult;
import com.itt.tds.core.TaskState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class SaveResult {
	static Logger logger = new TDSLogger().getLogger();

	private static final String TASK_ERROR_MSG = "taskErrMsg";
	private static final String TASK_ERROR_CODE = "taskErrorCode";
	private static final String TASK_OUTCOME = "taskOutcome";
	private static final String TASK_ID = "taskId";
	private static final String SUCCESS = "SUCCESS";

	public static TDSResponse addTaskResult(TDSRequest request) {
		TDSResponse response = null;
		CoOrdinator coOrdinator = new CoOrdinator();

		try {
			Node node = coOrdinator.getNodeFromRequest(request);
			if (node == null)
				return coOrdinator.getInvalidNodetResponse(request);

			TaskResult taskResult = new TaskResult();
			taskResult.setTaskId(Integer.parseInt(request.getParameters(TASK_ID)));
			taskResult.setTaskOutcome(Integer.parseInt(request.getParameters(TASK_OUTCOME)));
			taskResult.setErrorCode(Integer.parseInt(request.getParameters(TASK_ERROR_CODE)));
			taskResult.setErrorMessage(request.getParameters(TASK_ERROR_MSG));
			taskResult.setResultBuffer(request.getData());

			TDSTaskRepository taskRepo = new TDSTaskRepository();
			Task task = taskRepo.GetTaskById(taskResult.getTaskId());
			int assignedNodeId = task.getAssingedNodeId();
			if (assignedNodeId != node.getId())
				return coOrdinator.getInvalidNodetResponse(request);

			TDSTaskResultRepository taskResultRepo = new TDSTaskResultRepository();
			taskResultRepo.Add(taskResult);

			taskRepo.SetTaskStatus(task.getId(), TaskState.COMPLETED);

			response = Utility.prepareResponseFromRequest(request);
			response.setStatus(SUCCESS);

		} catch (DatabaseTransactionException e) {
			logger.error("Error while processing add request for node", e);
			response = coOrdinator.getUnableToPerformResponse(request);
		}
		return response;
	}

}
