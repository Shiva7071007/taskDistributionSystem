package com.itt.tds.coordinator.db.repository;

import java.util.*;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.core.Task;

/**
 * 
 */
public interface TaskRepository {

    public int Add(Task taskInstance) throws DatabaseTransactionException;

    public void Delete(int taskId) throws DatabaseTransactionException;

    public void Modify(Task taskInstance) throws DatabaseTransactionException;

    public void SetTaskStatus(int taskId, int status) throws DatabaseTransactionException;

    public List<Task> GetTasksByClientId(int clientId) throws DatabaseTransactionException;

    public Task GetTaskById(int taskId) throws DatabaseTransactionException;

    public List<Task> GetTasksByStatus(int status) throws DatabaseTransactionException;

    public List<Task> GetTasksByNodeId(int nodeId) throws DatabaseTransactionException;

    public void AssignNode(int nodeID, int taskId) throws DatabaseTransactionException;
}