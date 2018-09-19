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
     * @throws SQLException 
     */
    public int Add(Node node) throws SQLException;

    /**
     * @param node
     */
    public void Modify(Node node);

    /**
     * @param nodeId
     * @throws SQLException 
     */
    public void Delete(int nodeId) throws SQLException;

    /**
     * Returns a List of available Nodes 
     * @return
     * @throws SQLException 
     */
    public List<Node> GetAailableNodes() throws SQLException;

    /**
	 * Returns all nodes registered in the system.
	 * 
	 * @return
	 * @throws SQLException
	 */
    public List<Node> GetAllNodes() throws SQLException;

}