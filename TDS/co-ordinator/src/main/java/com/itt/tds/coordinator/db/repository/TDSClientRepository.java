package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itt.tds.core.Client;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.TDSExceptions.DatabaseConnectionException;
import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.coordinator.db.TDSDatabaseManager;

public class TDSClientRepository implements ClientRepository {
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public int Add(Client client) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int clientId = 0;
		String hostName = client.getHostName();
		String userName = client.getUserName();

		Connection conn = null;
		PreparedStatement insertClientStatement = null;
		ResultSet clientIdSet = null;

		String insertClientQuery = "INSERT INTO `tds`.`client` (`hostName`, `userName`) VALUES (?, ?)";

		try {
			conn = tdsDatabaseManager.getConnection();

			insertClientStatement = conn.prepareStatement(insertClientQuery, Statement.RETURN_GENERATED_KEYS);
			insertClientStatement.setString(1, hostName);
			insertClientStatement.setString(2, userName);
			int rowsAffected = insertClientStatement.executeUpdate();
			logger.debug("no of rows affected after inserting the client into the database : " + rowsAffected);

			clientIdSet = insertClientStatement.getGeneratedKeys();
			clientIdSet.next();
			clientId = clientIdSet.getInt(1);

			return clientId;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to insert client into the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(clientIdSet);
			tdsDatabaseManager.closePreparedStatement(insertClientStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Modify(Client client) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int id = client.getId();
		String newHostName = client.getHostName();
		String newClientName = client.getUserName();
		String modifyNodeQuery = "UPDATE `tds`.`client` SET `hostName` = ?, `userName` = ? WHERE (`clientId` = ?);";

		Connection conn = null;
		PreparedStatement modifyClientStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			modifyClientStatement = conn.prepareStatement(modifyNodeQuery);
			modifyClientStatement.setString(1, newHostName);
			modifyClientStatement.setString(2, newClientName);
			modifyClientStatement.setInt(3, id);
			int rowsAffected = modifyClientStatement.executeUpdate();
			logger.debug("no of rows affected after performing modification on client table is " + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to modify client into the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(modifyClientStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Delete(int clientId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		String deleteClientQuery = "DELETE FROM `tds`.`client` WHERE (`clientId` = ?)";
		Connection conn = null;
		PreparedStatement deleteClientStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			deleteClientStatement = conn.prepareStatement(deleteClientQuery);
			deleteClientStatement.setInt(1, clientId);
			int rowsAffected = deleteClientStatement.executeUpdate();
			logger.debug("rows affected after performing delete operation on client table : " + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to delete client from the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(deleteClientStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Client> GetClients() throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		List<Client> allNodeList = new ArrayList<Client>();
		String getAllNodeListQuery = "SELECT clientId,hostname,username FROM tds.client";

		Connection conn = null;
		PreparedStatement getClientStatement = null;
		ResultSet clientResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

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
		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get all client from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(clientResult);
			tdsDatabaseManager.closePreparedStatement(getClientStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

}
