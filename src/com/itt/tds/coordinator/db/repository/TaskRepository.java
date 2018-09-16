package com.itt.tds.coordinator.db.repository;

import java.util.*;

/**
 * 
 */
public interface TaskRepository {

    /**
     * @param taskInstance 
     * @return
     */
    public int Add(Task taskInstance);

    /**
     * @param taskId
     */
    public void Delete(int taskId);

    /**
     * @param taskInstance
     */
    public void Modify(Task taskInstance);

    /**
     * @param taskId 
     * @param status
     */
    public void SetTaskStatus(int taskId, TaskStatus status);

    /**
     * @param clientId 
     * @return
     */
    public List<Task> GetTasksByClientId(int clientId);

    /**
     * @param taskId 
     * @return
     */
    public Task GetTaskById(int taskId);

    /**
     * @param status 
     * @return
     */
    public List<Task> GetTasksByStatus(TaskStatus status);

    /**
     * @param nodeId 
     * @return
     */
    public Set<Task> GetTasksByNodeId(int nodeId);

    /**
     * @param node 
     * @param taskId
     */
    public void AssignNode(Node node, int taskId);

}