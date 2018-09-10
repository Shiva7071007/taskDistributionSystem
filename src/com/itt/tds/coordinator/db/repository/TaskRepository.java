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
     * @param taskInstance
     */
    public void Delete(Task taskInstance);

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

}