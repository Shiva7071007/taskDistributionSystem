package com.itt.tds.coordinator.db.repository;

import java.util.*;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.core.Node;

/**
 * 
 */
public interface NodeRepository {

    public int Add(Node node) throws DatabaseTransactionException;

    public void Modify(Node node) throws DatabaseTransactionException;

    public void Delete(int nodeId) throws DatabaseTransactionException;

    public List<Node> GetAailableNodes() throws DatabaseTransactionException;

    public List<Node> GetAllNodes() throws DatabaseTransactionException;
}