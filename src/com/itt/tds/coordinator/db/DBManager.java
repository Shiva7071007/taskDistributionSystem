package com.itt.tds.coordinator.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.itt.tds.exceptions.DBConnectionException;
import com.itt.tds.exceptions.DBConnectionException;

/**
 * 
 */
public interface DBManager {

	/**
	 * This function should create the instance of Connection object based on the
	 * Database connection parameters.
	 * 
	 * @return
	 * @throws DBConnectionException
	 * @throws SQLException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Connection getConnection() throws SQLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * @param conn
	 * @throws SQLException
	 */
	public void closeConnection(Connection conn) throws SQLException;

	// /**
	// * Should return the number of affected records due to the DML operation.
	// * @param conn
	// * @param query
	// * @return
	// */
	// public int executeDMLQuery(Connection conn, String query);
	//
	// /**
	// * @param conn Should return either DataSet(.NET C#) or a ResultSet(Java)
	// * @param query
	// * @return
	// */
	// public ResultSet executeSelectQuery(Connection conn, String query);

}