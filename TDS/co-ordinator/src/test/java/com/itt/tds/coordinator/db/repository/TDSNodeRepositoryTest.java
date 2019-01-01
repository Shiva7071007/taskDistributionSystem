package com.itt.tds.coordinator.db.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.itt.tds.core.Node;
import com.itt.tds.core.NodeState;

public class TDSNodeRepositoryTest {

	@Test
	public void testAdd() throws Exception {
		// arrange
		Node node = new Node();
		int id = 0;
		node.setiP("0.0.0.1");
		node.setPort(12345);
		node.setStatus(NodeState.AVAILABLE);

		TDSNodeRepository tdsNodeRepository = new TDSNodeRepository();

		// action
		id = tdsNodeRepository.Add(node);
		List<Node> nodeList = tdsNodeRepository.GetAllNodes();

		// assert
		for (Node node1 : nodeList) {
			if (node1.getId() == id) {
				assertEquals(node1.getiP(), node.getiP());
				assertEquals(node1.getPort(), node.getPort());
				assertEquals(node1.getStatus(), node.getStatus());
			}
		}

		tdsNodeRepository.Delete(id);
	}

	@Test
	public void testModify() throws Exception {
		// arrange
		Node node = new Node();
		int id = 0;
		node.setiP("0.0.0.1");
		node.setPort(12345);
		node.setStatus(NodeState.AVAILABLE);

		TDSNodeRepository tdsNodeRepository = new TDSNodeRepository();
		id = tdsNodeRepository.Add(node);

		Node newNode = new Node();
		newNode.setId(id);
		newNode.setiP("0.0.0.2");
		newNode.setPort(12346);
		newNode.setStatus(NodeState.BUSY);

		// action
		tdsNodeRepository.Modify(newNode);

		// assert
		List<Node> nodeList = tdsNodeRepository.GetAllNodes();

		for (Node node1 : nodeList) {
			if (node1.getId() == id) {
				assertTrue(node1.getiP() != node.getiP());
				assertTrue(node1.getPort() != node.getPort());
				assertTrue(node1.getStatus() != node.getStatus());
			}
		}
		tdsNodeRepository.Delete(id);
	}

	@Test
	public void testDelete() throws Exception {
		// arrange
		Node node = new Node();
		int id = 0;
		node.setiP("0.0.0.1");
		node.setPort(12345);
		node.setStatus(NodeState.AVAILABLE);

		TDSNodeRepository tdsNodeRepository = new TDSNodeRepository();
		id = tdsNodeRepository.Add(node);

		// action
		tdsNodeRepository.Delete(id);
		List<Node> nodeList = tdsNodeRepository.GetAllNodes();

		// assert
		for (Node node1 : nodeList) {
			assertTrue(node1.getId() != id);
		}
	}

	@Test
	public void testGetAailableNodes() throws Exception {
		// arrange
		Node node1 = new Node();
		int id1 = 0;
		node1.setiP("0.0.0.1");
		node1.setPort(12345);
		node1.setStatus(NodeState.NOT_OPERATIONAL);

		Node node2 = new Node();
		int id2 = 0;
		node2.setiP("0.0.0.2");
		node2.setPort(12345);
		node2.setStatus(NodeState.AVAILABLE);

		TDSNodeRepository tdsNodeRepository = new TDSNodeRepository();

		id1 = tdsNodeRepository.Add(node1);
		id2 = tdsNodeRepository.Add(node2);

		// action
		List<Node> nodeList = tdsNodeRepository.GetAllNodes();

		// assert
		for (Node node : nodeList) {
			assertTrue(node1.getId() != id1);
			if (node.getId() == id2) {
				assertEquals(node.getiP(), node2.getiP());
				assertEquals(node.getPort(), node2.getPort());
				assertEquals(node.getStatus(), node2.getStatus());
			}
		}

		tdsNodeRepository.Delete(id1);
		tdsNodeRepository.Delete(id2);
	}

	@Test
	public void testGetAllNodes() throws Exception {
		// arrange
		Node node1 = new Node();
		int id1 = 0;
		node1.setiP("0.0.0.1");
		node1.setPort(12345);
		node1.setStatus(NodeState.NOT_OPERATIONAL);

		Node node2 = new Node();
		int id2 = 0;
		node2.setiP("0.0.0.2");
		node2.setPort(12345);
		node2.setStatus(NodeState.BUSY);

		TDSNodeRepository tdsNodeRepository = new TDSNodeRepository();

		id1 = tdsNodeRepository.Add(node1);
		id2 = tdsNodeRepository.Add(node2);

		// action
		List<Node> nodeList = tdsNodeRepository.GetAllNodes();

		// assert
		for (Node node : nodeList) {
			if (node.getId() == id1) {
				assertEquals(node.getiP(), node1.getiP());
				assertEquals(node.getPort(), node1.getPort());
				assertEquals(node.getStatus(), node1.getStatus());
			}
			if (node.getId() == id2) {
				assertEquals(node.getiP(), node2.getiP());
				assertEquals(node.getPort(), node2.getPort());
				assertEquals(node.getStatus(), node2.getStatus());
			}
		}

		tdsNodeRepository.Delete(id1);
		tdsNodeRepository.Delete(id2);
	}

}
