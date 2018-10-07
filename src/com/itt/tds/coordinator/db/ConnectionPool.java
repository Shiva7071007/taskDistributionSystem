package com.itt.tds.coordinator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.cfg.TDSConfiguration;

public class ConnectionPool {
	List<Connection> availableConnections = new ArrayList<Connection>();

	public ConnectionPool() throws Exception {
		initializeConnectionPool();
	}

	private void initializeConnectionPool() throws Exception {
		while (!checkIfConnectionPoolIsFull()) {
			availableConnections.add(createNewConnectionForPool());
		}
	}

	private synchronized boolean checkIfConnectionPoolIsFull() throws Exception {
		final int MAX_POOL_SIZE = TDSConfiguration.getInstance().getMaxDBConnectionNumber();

		if (availableConnections.size() < MAX_POOL_SIZE) {
			return false;
		}

		return true;
	}

	// Creating a connection
	private Connection createNewConnectionForPool() throws Exception {
		Connection dbConnection = null;

		TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
		String dbConnectionString = tdsConfiguration.getDBConnectionString();

		dbConnection = DriverManager.getConnection(dbConnectionString);
		return dbConnection;
	}

	public synchronized Connection getConnectionFromPool() {
		Connection connection = null;
		if (availableConnections.size() > 0) {
			connection = (Connection) availableConnections.get(0);
			availableConnections.remove(0);
		}
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection) {
		availableConnections.add(connection);
	}
}
