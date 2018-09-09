package com.itt.tds.coordinator.db.repository;

import java.util.*;

/**
 * 
 */
public interface NodeToTaskMappingRepository {

    /**
     * @param node 
     * @param taskId
     */
    public void AssignNode(Node node, int taskId);

    /**
     * @param nodeId 
     * @return
     */
    public Set<Task> GetTasksByNodeId(int nodeId);

}