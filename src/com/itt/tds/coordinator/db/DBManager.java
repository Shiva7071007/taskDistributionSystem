package com.itt.tds.coordinator.db;

import java.util.*;

/**
 * 
 */
public interface DBManager {

    /**
     * This function should create the instance of Connection object based on the Database connection parameters.
     * @return
     */
    public Connection getConnection();

    /**
     * @param conn
     */
    public void closeConnection(Connection conn);

    /**
     * Should return the number of affected records due to the DML operation.
     * @param query 
     * @return
     */
    public int executeDMLQuery(String query);

    /**
     * @param query Should return either DataSet(.NET C#) or a ResultSet(Java)
     * @return
     */
    public ResultSet executeSelectQuery(String query);

}