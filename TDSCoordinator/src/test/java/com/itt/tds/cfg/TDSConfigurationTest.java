package com.itt.tds.cfg;

import static org.junit.Assert.*;

import org.junit.Test;

public class TDSConfigurationTest {

	@Test
	public void testGetInstance() {
		// act
		TDSConfiguration tdsCFG1 = TDSConfiguration.getInstance();
		TDSConfiguration tdsCFG2 = TDSConfiguration.getInstance();

		// assert
		assertSame(tdsCFG1, tdsCFG2);
	}

	@Test
	public void testGetDBConnectionString() throws Exception {
//		// arrange
//		String dbConnectionString = "jdbc:mysql://localhost:3306/tds?user=root&password=password";
//		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
//
//		// act
//		String result = tdsCFG.getDBConnectionString();
//
//		// assert
//		assertEquals(dbConnectionString, result);
	}

	@Test
	public void testGetMaxDBConnectionNumber() throws Exception {
//		// arrange
//		int maxDBConnectionnumber = 10;
//		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
//
//		// act
//		int dbConnectionNumber = tdsCFG.getMaxDBConnectionNumber();
//
//		// assert
//		assertEquals(maxDBConnectionnumber, dbConnectionNumber);
	}

}
