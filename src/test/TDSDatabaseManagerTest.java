package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import com.itt.tds.coordinator.db.TDSDatabaseManager;

class TDSDatabaseManagerTest {

	@Test
	void testGetInstance() {
		// act
		TDSDatabaseManager tdsDB1 = TDSDatabaseManager.getInstance();
		TDSDatabaseManager tdsDB2 = TDSDatabaseManager.getInstance();

		// assert
		assertEquals(tdsDB1.hashCode(), tdsDB2.hashCode());
		assertSame(tdsDB1, tdsDB2);
	}

	@Test
	void testGetConnection() throws Exception {
		// arrange
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		// act
		Connection conn = tdsDatabaseManager.getConnection();

		// assert
		assertNotNull(conn);
		assertTrue(conn.isValid(0));

	}

	@Test
	void testCloseConnection() throws Exception {
		// arrange
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = tdsDatabaseManager.getConnection();

		// act
		tdsDatabaseManager.closeConnection(conn);

		// assert
		assertTrue(conn.isClosed());
	}

}