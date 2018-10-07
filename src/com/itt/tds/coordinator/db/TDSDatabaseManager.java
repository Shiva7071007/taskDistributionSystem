package com.itt.tds.coordinator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.cfg.TDSConfiguration;

public class TDSDatabaseManager implements DBManager {

	private static volatile TDSDatabaseManager tdsDatabaseManagerInstance;

	 static List<Connection> availableConnections = new ArrayList<Connection>();

	/**
	 * Default constructor
	 */
	private TDSDatabaseManager() {
		if (tdsDatabaseManagerInstance != null) {
			throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
		}
	}

	/**
	 * Should return the instance of TDSDatabaseManager object, the code should make
	 * sure only one instance of the object in the application regardless of the
	 * number of times the getInstance() method is called on the object.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static TDSDatabaseManager getInstance() throws Exception {
		// Double check locking pattern
		if (tdsDatabaseManagerInstance == null) { // Check for the first time

			synchronized (TDSConfiguration.class) { // Check for the second time.
				// if there is no instance available... create new one
				if (tdsDatabaseManagerInstance == null) {
					tdsDatabaseManagerInstance = new TDSDatabaseManager();
					initializeConnectionPool();
				}
			}
		}
		return tdsDatabaseManagerInstance;
	}

	private static  void initializeConnectionPool() throws Exception {
		while (!checkIfConnectionPoolIsFull()) {
			availableConnections.add(createNewConnectionForPool());
		}
	}

	private synchronized static boolean checkIfConnectionPoolIsFull() throws Exception {
		final int MAX_POOL_SIZE = TDSConfiguration.getInstance().getMaxDBConnectionNumber();

		if (availableConnections.size() < MAX_POOL_SIZE) {
			return false;
		}
		return true;
	}

	private static Connection createNewConnectionForPool() throws Exception {
		Connection dbConnection = null;

		TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
		String dbConnectionString = tdsConfiguration.getDBConnectionString();

		dbConnection = DriverManager.getConnection(dbConnectionString);
		return dbConnection;
	}

	private synchronized Connection getConnectionFromPool() {
		Connection connection = null;
		if (availableConnections.size() > 0) {
			connection = (Connection) availableConnections.get(0);
			availableConnections.remove(0);
		}
		return connection;
	}

	private synchronized void returnConnectionToPool(Connection connection) {
		availableConnections.add(connection);
	}
	
	//static ConnectionPool pool = new ConnectionPool();

	@Override
	public Connection getConnection() throws Exception {
		// Connection dbConnection = null;
		//
		// TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
		// String dbConnectionString = tdsConfiguration.getDBConnectionString();
		//
		// dbConnection = DriverManager.getConnection(dbConnectionString);
		Connection connection = getConnectionFromPool();
		return connection;
	}

	@Override
	public void returnConnection(Connection conn) throws Exception {
		returnConnectionToPool(conn);
	}
}
