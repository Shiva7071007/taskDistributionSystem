package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseConnectionException;
import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.NodeState;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.core.Node;

public class TDSNodeRepository implements NodeRepository {
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public int Add(Node node) throws DatabaseTransactionException {
		String nodeIp = node.getiP();
		int nodePort = node.getPort();
		int nodeStatus = node.getStatus();

		int nodeId = 0;
		String insertNodeQuery = "INSERT INTO `tds`.`node` (`nodeIp`, `nodePort`, `nodeStatus`) VALUES (INET_ATON(?), ?, ?)";

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement insertNodeStatement = null;
		ResultSet nodeIdSet = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			insertNodeStatement = conn.prepareStatement(insertNodeQuery, Statement.RETURN_GENERATED_KEYS);
			insertNodeStatement.setString(1, nodeIp);
			insertNodeStatement.setInt(2, nodePort);
			insertNodeStatement.setInt(3, nodeStatus);
			int rowsAffected = insertNodeStatement.executeUpdate();
			logger.info("rows affected after inserting node into the database : " + rowsAffected);

			nodeIdSet = insertNodeStatement.getGeneratedKeys();
			nodeIdSet.next();
			nodeId = nodeIdSet.getInt(1);

			return nodeId;
		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to insert node into the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(nodeIdSet);
			tdsDatabaseManager.closePreparedStatement(insertNodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Modify(Node node) throws DatabaseTransactionException {
		int nodeId = node.getId();
		String newNodeIp = node.getiP();
		int newNodePort = node.getPort();
		int newNodeStatus = node.getStatus();

		String modifyNodeQuery = "UPDATE `tds`.`node` SET `nodeIp` = INET_ATON(?), `nodePort` = ?, `nodeStatus` = ? WHERE (`nodeId` = ?)";
		Connection conn = null;
		PreparedStatement modifyNodeStatement = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			modifyNodeStatement = conn.prepareStatement(modifyNodeQuery);
			modifyNodeStatement.setString(1, newNodeIp);
			modifyNodeStatement.setInt(2, newNodePort);
			modifyNodeStatement.setInt(3, newNodeStatus);
			modifyNodeStatement.setInt(4, nodeId);
			int rowsAffected = modifyNodeStatement.executeUpdate();
			logger.info("rows affected after modifying node into the database : " + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to modify node into the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(modifyNodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Delete(int nodeId) throws DatabaseTransactionException {
		Connection conn = null;
		PreparedStatement deleteNodeStatement = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			String deleteNodeQuery = "DELETE FROM `tds`.`node` WHERE (`nodeId` = ?)";
			deleteNodeStatement = conn.prepareStatement(deleteNodeQuery);
			deleteNodeStatement.setInt(1, nodeId);
			int rowsAffected = deleteNodeStatement.executeUpdate();
			logger.info("rows affected after deleting node from the database : " + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to delete node from the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(deleteNodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Node> GetAailableNodes() throws DatabaseTransactionException {
		List<Node> availableNodeList = new ArrayList<Node>();
		Connection conn = null;
		PreparedStatement getAvailableNodeStatement = null;
		ResultSet availableNodeResult = null;

		String getAvailableNodeQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node where nodeStatus = ?";
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			getAvailableNodeStatement = conn.prepareStatement(getAvailableNodeQuery);
			getAvailableNodeStatement.setInt(1, NodeState.AVAILABLE);

			availableNodeResult = getAvailableNodeStatement.executeQuery();
			while (availableNodeResult.next()) {
				Node node = new Node();
				node.setId(availableNodeResult.getInt("nodeId"));
				node.setiP(availableNodeResult.getString("inet_ntoa(nodeIp)"));
				node.setPort(availableNodeResult.getInt("nodePort"));
				node.setStatus(availableNodeResult.getInt("nodeStatus+0"));

				availableNodeList.add(node);
			}
			return availableNodeList;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get all available nodes from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(availableNodeResult);
			tdsDatabaseManager.closePreparedStatement(getAvailableNodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Node> GetAllNodes() throws DatabaseTransactionException {
		List<Node> allNodeList = new ArrayList<Node>();

		Connection conn = null;
		PreparedStatement getAllNodeStatement = null;
		ResultSet availableNodeResult = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		String getAllNodeListQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node";

		try {
			conn = tdsDatabaseManager.getConnection();

			getAllNodeStatement = conn.prepareStatement(getAllNodeListQuery);

			availableNodeResult = getAllNodeStatement.executeQuery();
			while (availableNodeResult.next()) {
				Node node = new Node();
				node.setId(availableNodeResult.getInt("nodeId"));
				node.setiP(availableNodeResult.getString("inet_ntoa(nodeIp)"));
				node.setPort(availableNodeResult.getInt("nodePort"));
				node.setStatus(availableNodeResult.getInt("nodeStatus+0"));

				allNodeList.add(node);
			}
			return allNodeList;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get all the nodes from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(availableNodeResult);
			tdsDatabaseManager.closePreparedStatement(getAllNodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}
}
