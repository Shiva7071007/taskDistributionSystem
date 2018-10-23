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
     * @throws Exception 
     */
    public Task GetTaskById(int taskId) throws Exception;

    /**
     * @param status 
     * @return
     * @throws Exception 
     */
    public List<Task> GetTasksByStatus(int status) throws Exception;

    /**
     * @param nodeId 
     * @return
     * @throws Exception 
     */
    public List<Task> GetTasksByNodeId(int nodeId) throws Exception;

    /**
     * @param node 
     * @param taskId
     * @throws Exception 
     */
    public void AssignNode(int nodeID, int taskId) throws Exception;

}