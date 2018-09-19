package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.NodeState;
import com.itt.tds.node.Node;

public class TDSNodeRepository implements NodeRepository {

	@Override
	public int Add(Node node) throws SQLException {
		String nodeIp = node.getiP();
		int nodePort = node.getPort();
		int nodeStatus = node.getStatus();

		int nodeId = 0;

		TDSDatabaseManager tdsDatabaseManager = new TDSDatabaseManager();
		Connection conn = tdsDatabaseManager.getConnection();
		conn.setAutoCommit(false);

		String insertNodeQuery = "INSERT INTO `tds`.`node` (`nodeIp`, `nodePort`, `nodeStatus`) VALUES (INET_ATON(?), ?, ?)";
		PreparedStatement insertNodeStatement = conn.prepareStatement(insertNodeQuery);
		insertNodeStatement.setString(1, nodeIp);
		insertNodeStatement.setInt(2, nodePort);
		insertNodeStatement.setInt(3, nodeStatus);
		int rowsAffected = insertNodeStatement.executeUpdate();
		if (rowsAffected != 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

		String getNodeIdQuery = "SELECT nodeId FROM tds.node where nodeIP = inet_aton(?)";
		PreparedStatement getNodeIdStatement = conn.prepareStatement(getNodeIdQuery);
		getNodeIdStatement.setString(1, nodeIp);
		ResultSet nodeIdResult = getNodeIdStatement.executeQuery();
		if (nodeIdResult.first()) {
			nodeId = nodeIdResult.getInt("nodeId");
		}
		tdsDatabaseManager.closeConnection(conn);
		return nodeId;
	}

	@Override
	public void Modify(Node node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Delete(int nodeId) throws SQLException {
		TDSDatabaseManager tdsDatabaseManager = new TDSDatabaseManager();
		Connection conn = tdsDatabaseManager.getConnection();
		conn.setAutoCommit(false);

		String deleteNodeQuery = "DELETE FROM `tds`.`node` WHERE (`nodeId` = ?)";
		PreparedStatement deleteNodeStatement = conn.prepareStatement(deleteNodeQuery);
		deleteNodeStatement.setInt(1, nodeId);
		int rowsAffected = deleteNodeStatement.executeUpdate();

		if (rowsAffected != 0) {
			conn.commit();
		} else {
			conn.rollback();
		}
		tdsDatabaseManager.closeConnection(conn);
	}

	@Override
	public List<Node> GetAailableNodes() throws SQLException {
		List<Node> availableNodeList = new ArrayList<Node>();

		TDSDatabaseManager tdsDatabaseManager = new TDSDatabaseManager();
		Connection conn = tdsDatabaseManager.getConnection();

		String getAvailableNodeQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node where nodeStatus = ?";
		PreparedStatement getAvailableNodeStatement = conn.prepareStatement(getAvailableNodeQuery);
		getAvailableNodeStatement.setInt(1, NodeState.AVAILABLE);

		ResultSet availableNodeResult = getAvailableNodeStatement.executeQuery();
		while (availableNodeResult.next()) {
			Node node = new Node();
			node.setId(availableNodeResult.getInt("nodeId"));
			node.setiP(availableNodeResult.getString("inet_ntoa(nodeIp)"));
			node.setPort(availableNodeResult.getInt("nodePort"));
			node.setStatus(availableNodeResult.getInt("nodeStatus+0"));

			availableNodeList.add(node);
		}
		tdsDatabaseManager.closeConnection(conn);
		return availableNodeList;
	}

	@Override
	public List<Node> GetAllNodes() throws SQLException {
		List<Node> allNodeList = new ArrayList<Node>();

		TDSDatabaseManager tdsDatabaseManager = new TDSDatabaseManager();
		Connection conn = tdsDatabaseManager.getConnection();

		String getAllNodeListQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node";
		PreparedStatement getAllNodeStatement = conn.prepareStatement(getAllNodeListQuery);

		ResultSet availableNodeResult = getAllNodeStatement.executeQuery();
		while (availableNodeResult.next()) {
			Node node = new Node();
			node.setId(availableNodeResult.getInt("nodeId"));
			node.setiP(availableNodeResult.getString("inet_ntoa(nodeIp)"));
			node.setPort(availableNodeResult.getInt("nodePort"));
			node.setStatus(availableNodeResult.getInt("nodeStatus+0"));

			allNodeList.add(node);
		}
		tdsDatabaseManager.closeConnection(conn);
		return allNodeList;
	}

}
