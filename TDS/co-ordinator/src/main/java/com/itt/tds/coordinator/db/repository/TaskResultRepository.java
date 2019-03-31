package com.itt.tds.coordinator.db.repository;

import java.util.List;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.core.TaskResult;

public interface TaskResultRepository {

	public void Add(TaskResult taskresultInstance) throws DatabaseTransactionException;
	
	public void Delete(int taskId) throws DatabaseTransactionException;
	
	public void Modify(TaskResult taskresultInstance) throws DatabaseTransactionException;
	
	public TaskResult getTaskResultByTaskId (int taskId) throws DatabaseTransactionException;
	
	public List<TaskResult> getTaskResultByErrCode(int errorCode) throws DatabaseTransactionException;

}
