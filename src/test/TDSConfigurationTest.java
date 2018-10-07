package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.itt.tds.cfg.TDSConfiguration;

class TDSConfigurationTest {

	@Test
	void testGetInstance() {
		// act
		TDSConfiguration tdsCFG1 = TDSConfiguration.getInstance();
		TDSConfiguration tdsCFG2 = TDSConfiguration.getInstance();

		// assert
		assertSame(tdsCFG1, tdsCFG2);
	}

	@Test
	void testGetDBConnectionString() throws Exception {
		// arrange
		String dbConnectionString = "jdbc:mysql://localhost:3306/tds?user=root&password=password";
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		
		// act
		String result = tdsCFG.getDBConnectionString();

		// assert
		assertEquals(dbConnectionString, result);
	}
	
	@Test 
	void testGetMaxDBConnectionNumber() throws Exception {
		// arrange 
		int maxDBConnectionnumber = 10;
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		
		// act
		int dbConnectionNumber = tdsCFG.getMaxDBConnectionNumber();
		
		// assert
		assertEquals(maxDBConnectionnumber, dbConnectionNumber);
	}
}
