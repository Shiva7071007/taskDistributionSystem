package com.itt.tds.coordinator.db.repository;

import java.util.*;

/**
 * 
 */
public interface NodeRepository {

    /**
     * @param node
     */
    public void Add(Node node);

    /**
     * @param node
     */
    public void Modify(Node node);

    /**
     * @param node
     */
    public void Delete(Node node);

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