package com.itt.tds.coordinator.db.repository;

import java.util.*;

/**
 * 
 */
public interface NodeRepository {

    /**
     * @param node 
     * @return
     */
    public int Add(Node node);

    /**
     * @param node
     */
    public void Modify(Node node);

    /**
     * @param nodeId
     */
    public void Delete(int nodeId);

    /**
     * Returns a List of available Nodes 
     * @return
     */
    public List<Node> GetAailableNodes();

    /**
     * Returns  all nodes registered in the system.
     * @return
     */
    public List<Node> GetAllNodes();

}