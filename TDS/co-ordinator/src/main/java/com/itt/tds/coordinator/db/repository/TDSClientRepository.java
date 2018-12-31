package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.itt.tds.core.Client;
import com.itt.tds.coordinator.db.TDSDatabaseManager;

public class TDSClientRepository implements ClientRepository {

	@Override
	public int Add(Client client) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int clientId = 0;
		String hostName = client.getHostName();
		String userName = client.getUserName();

		Connection conn = null;
		PreparedStatement insertClientStatement = null;
		ResultSet clientIdSet = null;

		try {
			conn = tdsDatabaseManager.getConnection();
			String insertClientQuery = "INSERT INTO `tds`.`client` (`hostName`, `userName`) VALUES (?, ?)";

			insertClientStatement = conn.prepareStatement(insertClientQuery, Statement.RETURN_GENERATED_KEYS);
			insertClientStatement.setString(1, hostName);
			insertClientStatement.setString(2, userName);
			int rowsAffected = insertClientStatement.executeUpdate();
			if (rowsAffected == 1) {
				clientIdSet = insertClientStatement.getGeneratedKeys();
				clientIdSet.next();
				clientId = clientIdSet.getInt(1);
			} else {
				// log(no row affected)
			}
			return clientId;
		} finally {
			clientIdSet.close();
			insertClientStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Modify(Client client) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int id = client.getId();
		String newHostName = client.getHostName();
		String newClientName = client.getUserName();

		Connection conn = null;
		PreparedStatement modifyClientStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String modifyNodeQuery = "UPDATE `tds`.`client` SET `hostName` = ?, `userName` = ? WHERE (`clientId` = ?);";
			modifyClientStatement = conn.prepareStatement(modifyNodeQuery);
			modifyClientStatement.setString(1, newHostName);
			modifyClientStatement.setString(2, newClientName);
			modifyClientStatement.setInt(3, id);
			int rowsAffected = modifyClientStatement.executeUpdate();

			if (rowsAffected == 1) {
				// do something
			} else {
				// log(no row affected)
			}
		} finally {
			modifyClientStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Delete(int clientId) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement deleteClientStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String deleteClientQuery = "DELETE FROM `tds`.`client` WHERE (`clientId` = ?)";
			deleteClientStatement = conn.prepareStatement(deleteClientQuery);
			deleteClientStatement.setInt(1, clientId);
			int rowsAffected = deleteClientStatement.executeUpdate();

			if (rowsAffected != 0) {
				// conn.commit();
			} else {
				// conn.rollback();
			}
		} finally {
			deleteClientStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Client> GetClients() throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		List<Client> allNodeList = new ArrayList<Client>();

		Connection conn = null;
		PreparedStatement getClientStatement = null;
		ResultSet clientResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String getAllNodeListQuery = "SELECT clientId,hostname,username FROM tds.client";
			getClientStatement = conn.prepareStatement(getAllNodeListQuery);

			clientResult = getClientStatement.executeQuery();
			while (clientResult.next()) {
				Client client = new Client();
				client.setId(clientResult.getInt("clientId"));
				client.setHostName(clientResult.getString("hostname"));
				client.setUserName(clientResult.getString("username"));

				allNodeList.add(client);
			}
			return allNodeList;
		} finally {
			clientResult.close();
			getClientStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

}
