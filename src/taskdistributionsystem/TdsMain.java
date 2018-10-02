package taskdistributionsystem;
import java.io.IOException;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xml.sax.SAXException;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.client.Client;;

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
		
		ArrayList<String> arr = new ArrayList<String>();
		
		arr.add("hi");
		arr.add("hello");
		arr.add("world");
		
		System.out.println(arr.toString());
		JSONArray jsarr = new JSONArray();
		jsarr.addAll(arr);
		System.out.println(jsarr);
		String str1 = jsarr.toJSONString();
		
		JSONArray jsonArray1 = new JSONArray();
		jsonArray1.add("hi");
		
		
		
		
	}

}
