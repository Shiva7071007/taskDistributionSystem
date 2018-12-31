package com.itt.tds.coordinator.db.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.itt.tds.core.Client;

public class TDSClientRepositoryTest {

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
				List<Client> clientList = tdsClientRepository.GetClients();

				// assert
				for (Client client : clientList) {
					if (client.getId() == id) {
						assertEquals(client.getUserName(), client1.getUserName());
						assertEquals(client.getHostName(), client1.getHostName());
					}
				}
				tdsClientRepository.Delete(id);
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
