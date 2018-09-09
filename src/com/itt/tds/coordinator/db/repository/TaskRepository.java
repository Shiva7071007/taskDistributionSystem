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
     * @param TaskStatus
     */
    public void SetTaskStatus(int taskId, int TaskStatus);

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
     * @param taskStatus 
     * @return
     */
    public List<Task> GetTasksByStatus(int taskStatus);

}