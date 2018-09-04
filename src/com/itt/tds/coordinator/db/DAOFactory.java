package com.itt.tds.coordinator.db;

import java.util.*;

/**
 * DAO Factory should create the instance of a request DAO class and return the instance. Should be a singleton class.
 */
public interface DAOFactory {


    /**
     * @return
     */
    public ClientRepository getClientDAO();

    /**
     * @return
     */
    public TaskRepository getTaskInstanceDAO();

    /**
     * @return
     */
    public NodeRepository getNodeDAO();

}