package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.coordinator.db.DBManagerImplimentation;
import com.itt.tds.core.Task;
import com.itt.tds.node.Node;

public class TaskRepositoryImpl implements TaskRepository{

	@Override
	public int Add(Task task) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
	    Connection conn =null;
		int result = 0;
	     String query = "INSERT INTO task (taskName,taskParameter,taskPath,taskState,userID,assignedNodeId) values(?,?,?,?,?,?)";  
		try {
		    conn  = objDBManager.getConnection();
		    PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setString(1, task.getTaskName());
		    ps.setString(2,  task.getTaskParameters());
		    ps.setString(3, task.getTaskExePath());
		    ps.setInt(4,  task.getTaskState());
		    ps.setInt(5, task.getUserId());
		    ps.setInt(6,  task.getNodeId());
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
	public void Delete(int id) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
    	int result = 0;
		String query = "DELETE FROM task WHERE taskId= ?";	
    	try {
		    conn  = objDBManager.getConnection(); 
		    PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setInt(1, id);
		    result=ps.executeUpdate();
		   	if (result > 0) 
		   	{
		   	    System.out.println("task is deleted successfully!");
		    }
    	}
    	finally 
		{
			objDBManager.closeConnection(conn);
		}
		
	}

	@Override
	public void Modify(Task task) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
		Connection conn =null;
		int result = 0;
        String query = "UPDATE task SET taskName =? , taskParameter = ?, taskPath = ?"
        		+ "taskState = ? , userID = ?, assignedNodeId = ? WHERE taskId = ?";    
	    try {
	        conn  = objDBManager.getConnection();
	        PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setString(1, task.getTaskName());
		    ps.setString(2,  task.getTaskParameters());
		    ps.setString(3, task.getTaskExePath());
		    ps.setInt(4,  task.getTaskState());
		    ps.setInt(5, task.getUserId());
		    ps.setInt(6,  task.getNodeId());
		    ps.setInt(7, task.getId());
		    result=ps.executeUpdate();
	        if(result>0)
	        {
			   System.out.println("task is Updated");
	        }
	    }
	    finally 
		{
			objDBManager.closeConnection(conn);
		}
		
	}

	@Override
	public void SetTaskStatus(int taskId, int TaskStatus) throws Exception {
	
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
		Connection conn =null;
		int result = 0;
        String query = "UPDATE task SET taskState = ?  WHERE taskId = ?";    
	    try {
	        conn  = objDBManager.getConnection();
	        PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setInt(1, TaskStatus);
		    ps.setInt(2, taskId);
		    result=ps.executeUpdate();
	        if(result>0)
	        {
			   System.out.println("task status is Updated");
	        }
	    }
	    finally 
		{
			objDBManager.closeConnection(conn);
		}		
		
	}

	@Override
	public List<Task> GetTasksByClientId(int clientId) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
		List<Task> taskList = new ArrayList<Task>();
		Task objectTask = new Task();
		String query = "SELECT * FROM task WHERE userID=?";
		try {
			 conn  = objDBManager.getConnection(); 
			 PreparedStatement ps=conn.prepareStatement(query);  
			 ps.setInt(1, clientId);
	     	 ResultSet rs = ps.executeQuery();
	    	 while(rs.next()) { 
	    		 objectTask.setId(rs.getInt(1));
	    		 objectTask.setTaskName(rs.getString(2));
	    		 objectTask.setTaskParameters(rs.getString(3));
	    		 objectTask.setTaskExePath(rs.getString(4));
	    		 objectTask.setTaskState(rs.getInt(5));
	    		 objectTask.setUserId(rs.getInt(6));
	    		 objectTask.setNodeId(rs.getInt(7));
	             taskList.add(objectTask);
	             objectTask = new Task(); 	  
	    	    }
		    } 
			finally 
			{
				objDBManager.closeConnection(conn);
			}
		return taskList;
	}

	@Override
	public Task GetTaskById(int taskId) throws Exception {
		DBManagerImplimentation objDBManager =DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
		Task objectTask = new Task();
		String query = "SELECT * FROM task WHERE taskId=?";
		try {
			 conn  = objDBManager.getConnection(); 
			 PreparedStatement ps=conn.prepareStatement(query);  
			 ps.setInt(1, taskId);
	     	 ResultSet rs = ps.executeQuery();
	    	 while(rs.next()) { 
	    		 objectTask.setId(rs.getInt(1));
	    		 objectTask.setTaskName(rs.getString(2));
	    		 objectTask.setTaskParameters(rs.getString(3));
	    		 objectTask.setTaskExePath(rs.getString(4));
	    		 objectTask.setTaskState(rs.getInt(5));
	    		 objectTask.setUserId(rs.getInt(6));
	    		 objectTask.setNodeId(rs.getInt(7));  
	    	    }
		    } 
			finally 
			{
				objDBManager.closeConnection(conn);
			}
		return objectTask;
	}

	@Override
	public List<Task> GetTasksByStatus(int taskStatus) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
		List<Task> taskList = new ArrayList<Task>();
		Task objectTask = new Task();
		String query = "SELECT * FROM task WHERE taskState=?";
		try {
			 conn  = objDBManager.getConnection(); 
			 PreparedStatement ps=conn.prepareStatement(query);  
			 ps.setInt(1, taskStatus);
	     	 ResultSet rs = ps.executeQuery();
	    	 while(rs.next()) { 
	    		 objectTask.setId(rs.getInt(1));
	    		 objectTask.setTaskName(rs.getString(2));
	    		 objectTask.setTaskParameters(rs.getString(3));
	    		 objectTask.setTaskExePath(rs.getString(4));
	    		 objectTask.setTaskState(rs.getInt(5));
	    		 objectTask.setUserId(rs.getInt(6));
	    		 objectTask.setNodeId(rs.getInt(7));
	             taskList.add(objectTask);
	             objectTask = new Task(); 	  
	    	    }
		    } 
			finally 
			{
				objDBManager.closeConnection(conn);
			}
		return taskList;
	}

	@Override
	public List<Task> GetTasksByNodeId(int nodeId) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
    	Connection conn =null;
		List<Task> taskList = new ArrayList<Task>();
		Task objectTask = new Task();
		String query = "SELECT * FROM task WHERE assignedNodeId=?";
		try {
			 conn  = objDBManager.getConnection(); 
			 PreparedStatement ps=conn.prepareStatement(query);  
			 ps.setInt(1, nodeId);
	     	 ResultSet rs = ps.executeQuery();
	    	 while(rs.next()) { 
	    		 objectTask.setId(rs.getInt(1));
	    		 objectTask.setTaskName(rs.getString(2));
	    		 objectTask.setTaskParameters(rs.getString(3));
	    		 objectTask.setTaskExePath(rs.getString(4));
	    		 objectTask.setTaskState(rs.getInt(5));
	    		 objectTask.setUserId(rs.getInt(6));
	    		 objectTask.setNodeId(rs.getInt(7));
	             taskList.add(objectTask);
	             objectTask = new Task(); 	  
	    	    }
		    } 
			finally 
			{
				objDBManager.closeConnection(conn);
			}
		return taskList;
	}

	@Override
	public void assignNode(Node node, Task task) throws Exception {
		DBManagerImplimentation objDBManager = DBManagerImplimentation.getInstance(); 
		NodeRepositoryImpl objectNode = new NodeRepositoryImpl();
		Connection conn =null;
		int result = 0;
        String query = "UPDATE task SET taskState = ? , assignedNodeId = ? WHERE taskId = ?";    
	    try {
	        conn  = objDBManager.getConnection();
	        PreparedStatement ps=conn.prepareStatement(query);  
		    ps.setInt(1, task.getTaskState());
		    ps.setInt(2, node.getId());
		    ps.setInt(3, task.getId());
		    result=ps.executeUpdate();
		    objectNode.Modify(node);
		  
	    }
	    finally 
		{
			objDBManager.closeConnection(conn);
		}		
		
	}

} 