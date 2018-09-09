package com.itt.tds.coordinator.db;

import java.sql.Connection;
import java.sql.ResultSet;
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
     * @param conn 
     * @param query 
     * @return
     */
    public int executeDMLQuery(Connection conn, String query);

    /**
     * @param conn Should return either DataSet(.NET C#) or a ResultSet(Java)
     * @param query 
     * @return
     */
    public ResultSet executeSelectQuery(Connection conn, String query);

}