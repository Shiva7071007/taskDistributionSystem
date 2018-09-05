package com.itt.tds.coordinator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itt.tds.cfg.TDSConfiguration;

public class TDSDatabaseManager implements DBManager {

	@Override
	public Connection getConnection() {
		Connection dbConnection = null;

		try {
			TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
			String dbConnectionString = tdsConfiguration.getDBConnectionString();

			dbConnection = DriverManager.getConnection(dbConnectionString);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	@Override
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("unable to close the connection");
			e.printStackTrace();
		}

	}

	@Override
	public int executeDMLQuery(String query) {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Connection dbConnection = null;
		try {
			dbConnection = getConnection();
			dbConnection.setAutoCommit(false);
			pstmt = dbConnection.prepareStatement(query);
			rowsAffected = pstmt.executeUpdate();
			dbConnection.commit();
		} catch (SQLException e) {
			try {
				System.out.println("Transaction Failed");
				dbConnection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		closeConnection(dbConnection);
		return rowsAffected;
	}

	@Override
	public ResultSet executeSelectQuery(String query) {
		ResultSet result = null;
		try {
			Connection dbConnection = getConnection();
			Statement stmt = dbConnection.createStatement();
			result = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
