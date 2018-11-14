package com.itt.tds.coordinator.db.repository;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import com.itt.tds.client.Client;

public class TDSClientRepositoryTest  extends DBTestCase  {
	
    public TDSClientRepositoryTest(String name) {
        super(name);
        System.out.println("test");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/test");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "password");
        //System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "test");
    }
	
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("tdsDataset.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
    
	 // Run once, e.g. Database connection, connection pool
    @BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
    }

    // Run once, e.g close connection, cleanup
    @AfterClass
    public static void runOnceAfterClass() {
        System.out.println("@AfterClass - runOnceAfterClass");
    }

    // Should rename to @BeforeTestMethod
    // e.g. Creating an similar object and share for all @Test
    @Before
    public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod");
    }

    // Should rename to @AfterTestMethod
    @After
    public void runAfterTestMethod() {
        System.out.println("@After - runAfterTestMethod");
    }

	@Test
	public void testAdd() throws Exception {
		// arrange
				Client client1 = new Client();
				int id = 0;
				client1.setHostName("hostName1");
				client1.setUserName("username1");

				TDSClientRepository tdsClientRepository = new TDSClientRepository();

				// action
				id = tdsClientRepository.Add(client1);
				 System.out.println(id);
				 assertThat(id, is(id));

//				List<Client> clientList = tdsClientRepository.GetClients();
//
//				// assert
//				for (Client client : clientList) {
//					if (client.getId() == id) {
//						assertEquals(client.getUserName(), client1.getUserName());
//						assertEquals(client.getHostName(), client1.getHostName());
//					}
//				}
//				tdsClientRepository.Delete(id);
	}

	@Test
	public void testModify() throws Exception {
		// arrange
				Client client1 = new Client();
				int id = 0;
				client1.setHostName("hostName");
				client1.setUserName("username");

				TDSClientRepository tdsClientRepository = new TDSClientRepository();
				id = tdsClientRepository.Add(client1);

				Client newClient = new Client();
				newClient.setId(id);
				newClient.setHostName("hostName1");
				newClient.setUserName("username1");

				// action
				tdsClientRepository.Modify(newClient);
				List<Client> clientList = tdsClientRepository.GetClients();

				// assert
				for (Client client : clientList) {
					if (client.getId() == id) {
						assertTrue(client.getUserName() != client1.getUserName());
						assertTrue(client.getHostName() != client1.getHostName());
					}
				}
				tdsClientRepository.Delete(id);
	}

	@Test
	public void testDelete() throws Exception {
		// arrange
				Client client1 = new Client();
				int id = 0;
				client1.setHostName("hostName1");
				client1.setUserName("username1");

				TDSClientRepository tdsClientRepository = new TDSClientRepository();
				id = tdsClientRepository.Add(client1);

				// action
				tdsClientRepository.Delete(id);
				List<Client> clientList = tdsClientRepository.GetClients();

				// assert
				for (Client client : clientList) {
					assertTrue(client.getId() != id);
				}
	}

	@Test
	public void testGetClients() throws Exception {
		// arrange
				Client client1 = new Client();
				int id1 = 0;
				client1.setHostName("hostName1");
				client1.setUserName("username1");

				Client client2 = new Client();
				int id2 = 0;
				client2.setHostName("hostName1");
				client2.setUserName("username1");

				TDSClientRepository tdsClientRepository = new TDSClientRepository();

				id1 = tdsClientRepository.Add(client1);
				id2 = tdsClientRepository.Add(client2);

				// action
				List<Client> clientList = tdsClientRepository.GetClients();

				// assert
				for (Client client : clientList) {
					if (client.getId() == id1) {
						assertEquals(client.getUserName(), client1.getUserName());
						assertEquals(client.getHostName(), client1.getHostName());
					}
					if (client.getId() == id2) {
						assertEquals(client.getUserName(), client2.getUserName());
						assertEquals(client.getHostName(), client2.getHostName());
					}
				}

				tdsClientRepository.Delete(id1);
				tdsClientRepository.Delete(id2);
	}

}
