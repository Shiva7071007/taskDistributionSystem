package com.itt.tds.coordinator.db.repository;

import java.util.List;

import com.itt.tds.core.TaskResult;

public interface TaskResultRepository {

	public void Add(TaskResult taskresultInstance) throws Exception;
	
	public void Delete(int taskId) throws Exception;
	
	public void Modify(TaskResult taskresultInstance) throws Exception;
	
	public TaskResult getTaskResultByTaskId (int taskId) throws Exception;
	
	public List<TaskResult> getTaskResultByErrCode(int errorCode) throws Exception;

}
