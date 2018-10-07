package taskdistributionsystem;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.coordinator.db.repository.TDSClientRepository;
import com.itt.tds.coordinator.db.repository.TDSTaskRepository;
import com.itt.tds.core.NodeState;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskState;
import com.itt.tds.node.Node;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import com.itt.tds.utility.Utility;

import org.xml.sax.SAXException;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.client.Client;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class TdsMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet rs = null;
		int nodeId =3;
		
		String nodeIp = "192.168.1.4";
		int nodePort = 8001;
		int nodeStatus = 1;
		
//		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
//		Connection conn = tdsDatabaseManager.getConnection();
		
//		String getAvailableNodeQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node where nodeStatus = ?";
//		PreparedStatement getAvailableNodeStatement = conn.prepareStatement(getAvailableNodeQuery);
//		getAvailableNodeStatement.setInt(1, NodeState.AVAILABLE);
//		
//		ResultSet availableNodeResult = getAvailableNodeStatement.executeQuery();
//		
//		while(availableNodeResult.next()) {
//			nodeId = availableNodeResult.getInt("nodeId");
//			nodeIp = availableNodeResult.getString("inet_ntoa(nodeIp)");
//			nodePort = availableNodeResult.getInt("nodePort");
//			nodeStatus = availableNodeResult.getInt("nodeStatus+0");
//			
//			System.out.println(nodeId);
//			System.out.println(nodeIp);
//			System.out.println(nodePort);
//			System.out.println(nodeStatus);
//			
//			
//		}
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		
		Connection conn = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn1 = tdsDatabaseManager.getConnection();
		System.out.println(conn1.isValid(2));
		Connection conn2 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn3 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn4 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn5 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn6 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn7 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn8 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn9 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn10 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn11 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));
		Connection conn12 = tdsDatabaseManager.getConnection();
		System.out.println(conn.isValid(2));

		
		
		
	}

}
