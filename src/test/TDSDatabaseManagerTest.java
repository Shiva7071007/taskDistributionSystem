package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itt.tds.coordinator.db.TDSDatabaseManager;

class TDSDatabaseManagerTest {

	private TDSDatabaseManager tdsDatabaseManager = new TDSDatabaseManager();

	@Test
	void testGetConnection() {	
		Connection conn = tdsDatabaseManager.getConnection();
		assertNotNull(conn);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	void testCloseConnection() {
		Connection conn = tdsDatabaseManager.getConnection();
		tdsDatabaseManager.closeConnection(conn);
		try {
			assertTrue(conn.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testExecuteDMLQuery() {
		String query = "show tables";
		Connection conn = tdsDatabaseManager.getConnection();
		int result = tdsDatabaseManager.executeDMLQuery(conn, query);
		assertEquals(result, 5);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void testExecuteSelectQuery() {
		Connection conn = tdsDatabaseManager.getConnection();
		ResultSet rs = null;
		String query = "show tables";
		rs = tdsDatabaseManager.executeSelectQuery(conn, query);
		assertNotNull(rs);
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
