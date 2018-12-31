package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.NodeState;
import com.itt.tds.core.Node;

public class TDSNodeRepository implements NodeRepository {

	@Override
	public int Add(Node node) throws Exception {
		String nodeIp = node.getiP();
		int nodePort = node.getPort();
		int nodeStatus = node.getStatus();

		int nodeId = 0;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement insertNodeStatement = null;
		ResultSet nodeIdSet = null;
		// conn.setAutoCommit(false);
		try {
			conn = tdsDatabaseManager.getConnection();
			String insertNodeQuery = "INSERT INTO `tds`.`node` (`nodeIp`, `nodePort`, `nodeStatus`) VALUES (INET_ATON(?), ?, ?)";
			insertNodeStatement = conn.prepareStatement(insertNodeQuery, Statement.RETURN_GENERATED_KEYS);
			insertNodeStatement.setString(1, nodeIp);
			insertNodeStatement.setInt(2, nodePort);
			insertNodeStatement.setInt(3, nodeStatus);
			int rowsAffected = insertNodeStatement.executeUpdate();
			if (rowsAffected == 1) {
				// conn.commit();
				nodeIdSet = insertNodeStatement.getGeneratedKeys();
				nodeIdSet.next();
				nodeId = nodeIdSet.getInt(1);
			} else {
				// log(no row affected)
			}
		} finally {
			nodeIdSet.close();
			insertNodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
		return nodeId;
	}

	@Override
	public void Modify(Node node) throws Exception {
		int nodeId = node.getId();
		String newNodeIp = node.getiP();
		int newNodePort = node.getPort();
		int newNodeStatus = node.getStatus();

		Connection conn = null;
		PreparedStatement modifyNodeStatement = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			String modifyNodeQuery = "UPDATE `tds`.`node` SET `nodeIp` = INET_ATON(?), `nodePort` = ?, `nodeStatus` = ? WHERE (`nodeId` = ?)";
			modifyNodeStatement = conn.prepareStatement(modifyNodeQuery);
			modifyNodeStatement.setString(1, newNodeIp);
			modifyNodeStatement.setInt(2, newNodePort);
			modifyNodeStatement.setInt(3, newNodeStatus);
			modifyNodeStatement.setInt(4, nodeId);
			int rowsAffected = modifyNodeStatement.executeUpdate();

			if (rowsAffected == 1) {
				// conn.commit();
			} else {
				// log(no row affected)
			}

		} finally {
			modifyNodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Delete(int nodeId) throws Exception {
		Connection conn = null;
		PreparedStatement deleteNodeStatement = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			String deleteNodeQuery = "DELETE FROM `tds`.`node` WHERE (`nodeId` = ?)";
			deleteNodeStatement = conn.prepareStatement(deleteNodeQuery);
			deleteNodeStatement.setInt(1, nodeId);
			int rowsAffected = deleteNodeStatement.executeUpdate();

			if (rowsAffected != 0) {
				// conn.commit();
			} else {
				// conn.rollback();
			}
		} finally {
			deleteNodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Node> GetAailableNodes() throws Exception {
		List<Node> availableNodeList = new ArrayList<Node>();
		Connection conn = null;
		PreparedStatement getAvailableNodeStatement = null;
		ResultSet availableNodeResult = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			String getAvailableNodeQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node where nodeStatus = ?";
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
		} finally {
			availableNodeResult.close();
			getAvailableNodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
		return availableNodeList;
	}

	@Override
	public List<Node> GetAllNodes() throws Exception {
		List<Node> allNodeList = new ArrayList<Node>();

		Connection conn = null;
		PreparedStatement getAllNodeStatement = null;
		ResultSet availableNodeResult = null;

		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		try {
			conn = tdsDatabaseManager.getConnection();

			String getAllNodeListQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node";
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
		} finally {
			availableNodeResult.close();
			getAllNodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
		return allNodeList;
	}
}
