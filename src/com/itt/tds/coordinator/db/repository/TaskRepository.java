package com.itt.tds.coordinator.db.repository;

import java.sql.SQLException;
import java.util.*;

import com.itt.tds.core.Task;

/**
 * 
 */
public interface TaskRepository {

    /**
     * @param taskInstance 
     * @return
     * @throws Exception 
     */
    public int Add(Task taskInstance) throws Exception;

    /**
     * @param taskId
     * @throws Exception 
     */
    public void Delete(int taskId) throws Exception;

    /**
     * @param taskInstance
     * @throws Exception 
     */
    public void Modify(Task taskInstance) throws Exception;

    /**
     * @param taskId 
     * @param status
     * @throws Exception 
     */
    public void SetTaskStatus(int taskId, int status) throws Exception;

    /**
     * @param clientId 
     * @return
     * @throws Exception 
     */
    public List<Task> GetTasksByClientId(int clientId) throws Exception;

    /**
     * @param taskId 
     * @return
     */
    public Task GetTaskById(int taskId);

    /**
     * @param status 
     * @return
     */
    public List<Task> GetTasksByStatus(int status);

    /**
     * @param nodeId 
     * @return
     */
    public List<Task> GetTasksByNodeId(int nodeId);

    /**
     * @param node 
     * @param taskId
     * @throws Exception 
     */
    public void AssignNode(int nodeID, int taskId) throws Exception;

}