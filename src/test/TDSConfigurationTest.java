package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.itt.tds.cfg.TDSConfiguration;

class TDSConfigurationTest {

	@Test
	void testGetInstance() {
		TDSConfiguration tdsCFG1 = TDSConfiguration.getInstance();
		TDSConfiguration tdsCFG2 = TDSConfiguration.getInstance();
		TDSConfiguration tdsCFG3 = TDSConfiguration.getInstance();
		TDSConfiguration tdsCFG4 = TDSConfiguration.getInstance();
		
		assertEquals(tdsCFG1.hashCode(), tdsCFG2.hashCode());
		assertSame(tdsCFG3, tdsCFG4);
	}

	@Test
	void testGetDBConnectionString() {
		String dbConnectionString = "jdbc:mysql://localhost:3306/tds?user=root&password=password";
		
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		String result = tdsCFG.getDBConnectionString();
		
		assertEquals(dbConnectionString, result);
	}
}
