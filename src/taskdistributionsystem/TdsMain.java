package taskdistributionsystem;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.NodeState;

import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.itt.tds.cfg.TDSConfiguration;;

public class TdsMain {

	public static void main(String[] args) throws SQLException, ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hello");
		
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet rs = null;
		int nodeId =3;
		
		String nodeIp = "192.168.1.4";
		int nodePort = 8001;
		int nodeStatus = 1;
		
		TDSDatabaseManager tdsDatabaseManager = new TDSDatabaseManager();
		Connection conn = tdsDatabaseManager.getConnection();
		
		String getAvailableNodeQuery = "SELECT nodeId,inet_ntoa(nodeIp),nodePort, nodeStatus+0 FROM tds.node where nodeStatus = ?";
		PreparedStatement getAvailableNodeStatement = conn.prepareStatement(getAvailableNodeQuery);
		getAvailableNodeStatement.setInt(1, NodeState.AVAILABLE);
		
		ResultSet availableNodeResult = getAvailableNodeStatement.executeQuery();
		
		while(availableNodeResult.next()) {
			nodeId = availableNodeResult.getInt("nodeId");
			nodeIp = availableNodeResult.getString("inet_ntoa(nodeIp)");
			nodePort = availableNodeResult.getInt("nodePort");
			nodeStatus = availableNodeResult.getInt("nodeStatus+0");
			
			System.out.println(nodeId);
			System.out.println(nodeIp);
			System.out.println(nodePort);
			System.out.println(nodeStatus);
			
			
		}
		
		
		
		// TDSConfiguration singleton check
		TDSConfiguration tdsConfiguration1 = TDSConfiguration.getInstance();
		TDSConfiguration tdsConfiguration2 = TDSConfiguration.getInstance();
		
		System.out.println("Instance 1 hash:" + tdsConfiguration1.hashCode());
        System.out.println("Instance 2 hash:" + tdsConfiguration2.hashCode());  
		
		
		
	
		
	}

}
