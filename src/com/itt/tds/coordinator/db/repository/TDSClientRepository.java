package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.client.Client;
import com.itt.tds.coordinator.db.DBManagerImplimentation;
import com.itt.tds.node.Node;

public class ClientRepositoryImpl implements ClientRepository{

	@Override
	public int Add(Client client) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
	    Connection conn =null;
		int result = 0;
	     String query = "INSERT INTO client (hostName,userName) values(?,?)";  
		try {
		    conn  = objDBManager.getConnection();     
		    PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setString(1, client.getHostName());
		    ps.setString(2, client.getUserName());
		    result=ps.executeUpdate();
	        if(result>0)
	        { 
			   ResultSet rs = ps.getGeneratedKeys();	
			   while(rs.next())
			   {
	           result = rs.getInt(1);
			   }
	        }
		}
		finally 
		{
			objDBManager.closeConnection(conn);
		}
		return result;
	}

	@Override
	public void Modify(Client client) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance();  
		Connection conn =null;
		int result = 0;
        String query = "UPDATE client SET hostName= ? , userName = ?  WHERE clientId = ?";    
	    try {
	        conn  = objDBManager.getConnection();
		    PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setString(1, client.getHostName());
		    ps.setString(2, client.getUserName());
		    ps.setInt(3, client.getId());
		    result=ps.executeUpdate();
	        if(result>0)
	        {
			   System.out.println("Client is Updated");
	        }
	    }
	    finally 
		{
			objDBManager.closeConnection(conn);
		}
		
	}

	@Override
	public void Delete(int id) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
    	int result = 0;
		String query = "DELETE FROM client WHERE clientId= ?";	
    	try {
		    conn  = objDBManager.getConnection(); 
		    PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setInt(1, id);
		    result=ps.executeUpdate();
		   	if (result > 0) 
		   	{
		   	    System.out.println("Client is deleted successfully!");
		    }
    	}
    	finally 
		{
			objDBManager.closeConnection(conn);
		}
		
	}

	@Override
	public List<Client> GetClients() throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
		List<Client> clientList = new ArrayList<Client>();
		Client objectClient = new Client();
		String query = "SELECT * FROM client";
		try {
			 conn  = objDBManager.getConnection(); 
			 PreparedStatement ps=conn.prepareStatement(query);  
	     	 ResultSet rs = ps.executeQuery();
	    	 while(rs.next()) { 
	    		 objectClient.setId(rs.getInt(1));
	    		 objectClient.setHostName(rs.getString(2));
	    		 objectClient.setUserName(rs.getString(2));
	    		 clientList.add(objectClient); 	    
	    		 objectClient = new Client(); 
	    	    }
		    } 
			finally 
			{
				objDBManager.closeConnection(conn);
			}
		return clientList;
	}

} 