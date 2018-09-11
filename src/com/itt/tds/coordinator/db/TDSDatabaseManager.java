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
			System.out.println("unable to close the connection");
			e.printStackTrace();
		}
	}

	@Override
	public int executeDMLQuery(Connection conn, String query) {
		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(query);
			rowsAffected = pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				System.out.println("Transaction Failed");
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowsAffected;
	}

	@Override
	public ResultSet executeSelectQuery(Connection conn, String query) {
		ResultSet result = null;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
