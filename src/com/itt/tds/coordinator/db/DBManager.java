package com.itt.tds.coordinator.db;

import java.sql.Connection;

/**
 * 
 */
public interface DBManager {

	/**
	 * This function should create the instance of Connection object based on the
	 * Database connection parameters.
	 * 
	 * @return
	 * @throws Exception 
	 */
	public Connection getConnection() throws Exception;

	/**
	 * @param conn
	 * @throws Exception 
	 */
	public void returnConnection(Connection conn) throws Exception;

}