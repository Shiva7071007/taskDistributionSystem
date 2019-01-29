package com.itt.tds.coordinator.db.repository;

import java.util.List;

import com.itt.tds.core.TaskResult;

public interface TaskResultRepository {

	public void Add(TaskResult taskresultInstance) throws Exception;
	
	public void Delete(int taskId) throws Exception;
	
	public void Modify(TaskResult taskresultInstance) throws Exception;
	
	public TaskResult getTaskResultById (int taskId);
	
	public List<TaskResult> getTaskResultByErrCode(int errorCode);
	
	public List<TaskResult> getallTaskResult();
}
