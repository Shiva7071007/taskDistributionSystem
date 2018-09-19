package com.itt.tds.coordinator.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.exceptions.DBConnectionException;

public class TDSDatabaseManager implements DBManager {

	@Override
	public Connection getConnection() {
		Connection dbConnection = null;

		TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
		String dbConnectionString = tdsConfiguration.getDBConnectionString();

		dbConnection = DriverManager.getConnection(dbConnectionString);
		return dbConnection;
	}

	@Override
	public void closeConnection(Connection conn) {
		conn.close();
	}

	// @Override
	// public int executeDMLQuery(Connection conn, String query) {
	// int rowsAffected = 0;
	// PreparedStatement pstmt = null;
	//
	// try {
	// conn.setAutoCommit(false);
	// pstmt = conn.prepareStatement(query);
	// rowsAffected = pstmt.executeUpdate();
	// conn.commit();
	// } catch (SQLException e) {
	// try {
	// System.out.println("Transaction Failed");
	// conn.rollback();
	// } catch (SQLException e1) {
	// e1.printStackTrace();
	// }
	// } finally {
	// try {
	// pstmt.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// return rowsAffected;
	// }
	//
	// @Override
	// public ResultSet executeSelectQuery(Connection conn, String query) {
	// ResultSet result = null;
	// try {
	// Statement stmt = conn.createStatement();
	// result = stmt.executeQuery(query);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

}
