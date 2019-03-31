package com.itt.tds.coordinator.db;

import java.sql.Connection;

import com.itt.tds.TDSExceptions.DatabaseConnectionException;

/**
 * 
 */
public interface DBManager {

	/**
	 * This function should create the instance of Connection object based on the
	 * Database connection parameters.
	 * 
	 * @return
	 * @throws DatabaseConnectionException 
	 * @throws Exception 
	 */
	public Connection getConnection() throws DatabaseConnectionException;

	/**
	 * @param conn
	 * @throws Exception 
	 */
	public void closeConnection(Connection conn);

}