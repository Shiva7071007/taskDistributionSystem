package com.itt.tds.coordinator.db.repository;

import java.sql.SQLException;
import java.util.*;

import com.itt.tds.node.Node;

/**
 * 
 */
public interface NodeRepository {

    /**
     * @param node 
     * @return
     * @throws Exception 
     */
    public int Add(Node node) throws SQLException, Exception;

    /**
     * @param node
     * @throws Exception 
     */
    public void Modify(Node node) throws Exception;

    /**
     * @param nodeId
     * @throws Exception 
     */
    public void Delete(int nodeId) throws Exception;

    /**
     * Returns a List of available Nodes 
     * @return
     * @throws Exception 
     */
    public List<Node> GetAailableNodes() throws Exception;

    /**
	 * Returns all nodes registered in the system.
	 * 
	 * @return
     * @throws Exception 
	 */
    public List<Node> GetAllNodes() throws Exception;

}