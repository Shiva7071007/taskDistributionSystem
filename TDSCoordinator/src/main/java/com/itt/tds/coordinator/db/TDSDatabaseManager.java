package com.itt.tds.coordinator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import com.itt.tds.cfg.TDSConfiguration;

public class TDSDatabaseManager implements DBManager {
	
	private static volatile TDSDatabaseManager tdsDatabaseManagerInstance;
	
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
	 */
	public static TDSDatabaseManager getInstance() {
		// Double check locking pattern
		if (tdsDatabaseManagerInstance == null) { // Check for the first time

			synchronized (TDSConfiguration.class) { // Check for the second time.
				// if there is no instance available... create new one
				if (tdsDatabaseManagerInstance == null)
					tdsDatabaseManagerInstance = new TDSDatabaseManager();
			}
		}
		return tdsDatabaseManagerInstance;
	}

	@Override
	public Connection getConnection() throws Exception {
		Connection dbConnection = null;

		TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
		String dbConnectionString = tdsConfiguration.getDBConnectionString();

		dbConnection = DriverManager.getConnection(dbConnectionString);
		return dbConnection;
	}

	@Override
	public void closeConnection(Connection conn) throws Exception {
		conn.close();
	}
}
