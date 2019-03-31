package com.itt.tds.coordinator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseConnectionException;
import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class TDSDatabaseManager implements DBManager {

	private static volatile TDSDatabaseManager tdsDatabaseManagerInstance;

	private static int MAXIMUM_ATTEMPT = 5;
	private static int SLEEP_DURATION = 2;
	static Logger logger = new TDSLogger().getLogger();

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
	public Connection getConnection() throws DatabaseConnectionException {
		Connection dbConnection = null;

		TDSConfiguration tdsConfiguration = TDSConfiguration.getInstance();
		String dbConnectionString = tdsConfiguration.getDBConnectionString();

		int reTryCount = 0;
		while (reTryCount++ < MAXIMUM_ATTEMPT) {
			try {

				dbConnection = DriverManager.getConnection(dbConnectionString);
				reTryCount = MAXIMUM_ATTEMPT;

			} catch (SQLException e) {
				if (reTryCount - 1 < MAXIMUM_ATTEMPT) {
					logger.error("failed to get database connection. retrying in " + SLEEP_DURATION + " seconds");
					Utility.sleep(SLEEP_DURATION);
				} else {
					logger.error("Failed to get database connection.", e);
					throw new DatabaseConnectionException("Unable to open connection with the database.", e);
				}
			}
		}

		return dbConnection;
	}

	@Override
	public void closeConnection(Connection conn) {
		int reTryCount = 0;
		while (reTryCount++ < MAXIMUM_ATTEMPT) {
			try {

				conn.close();
				reTryCount = MAXIMUM_ATTEMPT;

			} catch (SQLException e) {
				if (reTryCount - 1 < MAXIMUM_ATTEMPT)
					logger.error("failed to close database connection. retrying in " + SLEEP_DURATION + " seconds");
				else
					logger.error("Failed to close database connection.", e);
				Utility.sleep(SLEEP_DURATION);
			}
		}
	}

	public void closePreparedStatement(PreparedStatement pStmt) {
		int reTryCount = 0;
		while (reTryCount++ < MAXIMUM_ATTEMPT) {
			try {

				pStmt.close();
				reTryCount = MAXIMUM_ATTEMPT;

			} catch (SQLException e) {
				if (reTryCount - 1 < MAXIMUM_ATTEMPT)
					logger.error("failed to close Prepared statement. retrying in " + SLEEP_DURATION + " seconds");
				else
					logger.error("Failed to close Prepared Statement.", e);
				Utility.sleep(SLEEP_DURATION);
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		int reTryCount = 0;
		while (reTryCount++ < MAXIMUM_ATTEMPT) {
			try {

				rs.close();
				reTryCount = MAXIMUM_ATTEMPT;

			} catch (SQLException e) {
				if (reTryCount - 1 < MAXIMUM_ATTEMPT)
					logger.error("failed to close Result set. retrying in " + SLEEP_DURATION + " seconds");
				else
					logger.error("Failed to close Result set.", e);
				Utility.sleep(SLEEP_DURATION);
			}
		}
	}
}
