package com.itt.tds.coordinator.db;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

public class TDSDatabaseManagerTest {

	@Test
	public void testGetInstance() {
		// act
		TDSDatabaseManager tdsDB1 = TDSDatabaseManager.getInstance();
		TDSDatabaseManager tdsDB2 = TDSDatabaseManager.getInstance();

		// assert
		assertSame(tdsDB1, tdsDB2);
	}

	@Test
	public void testGetConnection() throws Exception {
		// arrange
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		// act
		Connection conn = tdsDatabaseManager.getConnection();

		// assert
		assertTrue(conn.isValid(1));
	}

	@Test
	public void testCloseConnection() throws Exception {
		// arrange
				TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
				Connection conn = tdsDatabaseManager.getConnection();

				// act
				tdsDatabaseManager.closeConnection(conn);

				// assert
				assertTrue(conn.isClosed());
	}

}
