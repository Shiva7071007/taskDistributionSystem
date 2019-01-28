package com.itt.tds.coordinator.db.repository;

import java.util.List;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.TaskOutcome;
import com.itt.tds.core.TaskResult;

public class TDSTaskResultRepository implements TaskResultRepository {

	@Override
	public void Add(TaskResult taskresultInstance) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int taskId =  taskresultInstance.getTaskId();
		TaskOutcome taskOutcome = taskresultInstance.getTaskOutcome();
	}

	@Override
	public void Delete(int taskId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void Modify(TaskResult taskresultInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public TaskResult getTaskResultById(int taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskResult> getTaskResultByErrCode(int errorCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
