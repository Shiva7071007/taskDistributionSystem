package com.itt.tds.coordinator.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.Task;

public class TDSTaskRepository implements TaskRepository {

	private TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

	@Override
	public int Add(Task taskInstance) throws Exception {
		int taskId = 0;
		String taskName = taskInstance.getTaskName();
		String taskParameters = arrayListToJSONArray(taskInstance.getTaskParameters());
		String taskPath = taskInstance.getTaskExePath();
		int taskState = taskInstance.getTaskState();
		int userID = taskInstance.getUserId();
		int assignedNodeId = taskInstance.getAssingedNodeId();

		Connection conn = null;
		PreparedStatement insertTaskStatement = null;
		ResultSet taskIdSet = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String insertTaskQuery = "INSERT INTO `tds`.`task` (`taskName`, `taskParameter`, `taskPath`, `taskState`, `userID`, `assignedNodeId`) VALUES (?, ?, ?, ?, ?, ?)";

			insertTaskStatement = conn.prepareStatement(insertTaskQuery);
			insertTaskStatement.setString(1, taskName);
			insertTaskStatement.setString(2, taskParameters);
			insertTaskStatement.setString(3, taskPath);
			insertTaskStatement.setInt(4, taskState);
			insertTaskStatement.setInt(5, userID);
			insertTaskStatement.setInt(6, assignedNodeId);

			int rowsAffected = insertTaskStatement.executeUpdate();
			if (rowsAffected == 1) {
				taskIdSet = insertTaskStatement.getGeneratedKeys();
				taskIdSet.next();
				taskId = taskIdSet.getInt(1);
			} else {
				// log(no row affected)
			}

			return taskId;
		} finally {
			taskIdSet.close();
			insertTaskStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@SuppressWarnings("unchecked")
	private String arrayListToJSONArray(ArrayList<String> taskParameters) {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(taskParameters);
		return jsonArray.toJSONString();
	}

	@Override
	public void Delete(int taskId) throws Exception {
		Connection conn = null;
		PreparedStatement deleteTaskStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String deleteTaskQuery = "DELETE FROM `tds`.`task` WHERE (`taskId` = ?)";
			deleteTaskStatement = conn.prepareStatement(deleteTaskQuery);
			deleteTaskStatement.setInt(1, taskId);
			int rowsAffected = deleteTaskStatement.executeUpdate();

			if (rowsAffected != 0) {
				// to do
			} else {
				// to do
			}
		} finally {
			deleteTaskStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Modify(Task taskInstance) throws Exception {
		int taskId = taskInstance.getId();
		String newTaskName = taskInstance.getTaskName();
		String newTaskParameters = arrayListToJSONArray(taskInstance.getTaskParameters());
		String newTtaskPath = taskInstance.getTaskExePath();
		int newTaskState = taskInstance.getTaskState();
		int newUserID = taskInstance.getUserId();
		int newAssignedNodeId = taskInstance.getAssingedNodeId();

		Connection conn = null;
		PreparedStatement modifyTaskStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String modifyTaskQuery = "UPDATE `tds`.`task` SET `taskName` = ?, `taskParameter` = ?, `taskPath` = ?, `taskState` = ?, `userID` = ?, `assignedNodeId` = ? WHERE (`taskId` = ?)";

			modifyTaskStatement = conn.prepareStatement(modifyTaskQuery);
			modifyTaskStatement.setString(1, newTaskName);
			modifyTaskStatement.setString(2, newTaskParameters);
			modifyTaskStatement.setString(3, newTtaskPath);
			modifyTaskStatement.setInt(4, newTaskState);
			modifyTaskStatement.setInt(5, newUserID);
			modifyTaskStatement.setInt(6, newAssignedNodeId);
			modifyTaskStatement.setInt(7, taskId);

			int rowsAffected = modifyTaskStatement.executeUpdate();

			if (rowsAffected == 1) {
				// conn.commit();
			} else {
				// log(no row affected)
			}

		} finally {
			modifyTaskStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void SetTaskStatus(int taskId, int status) throws Exception {

		Connection conn = null;
		PreparedStatement updateTaskStatusQueryStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String updateTaskStatusQuery = "UPDATE `tds`.`task` SET `taskState` = ? WHERE (`taskId` = ?)";

			updateTaskStatusQueryStatement = conn.prepareStatement(updateTaskStatusQuery);
			updateTaskStatusQueryStatement.setInt(1, status);
			updateTaskStatusQueryStatement.setInt(2, taskId);

			int rowsAffected = updateTaskStatusQueryStatement.executeUpdate();

			if (rowsAffected == 1) {
				// conn.commit();
			} else {
				// log(no row affected)
			}

		} finally {
			updateTaskStatusQueryStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Task> GetTasksByClientId(int clientId) throws Exception {
		List<Task> tasksByClientId = new ArrayList<Task>();
		Connection conn = null;
		PreparedStatement getTasksByClientIdStatement = null;
		ResultSet getTasksByClientIdResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String GetTasksByClientIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where userID = ?";
			getTasksByClientIdStatement = conn.prepareStatement(GetTasksByClientIdQuery);
			getTasksByClientIdStatement.setInt(1, clientId);

			getTasksByClientIdResult = getTasksByClientIdStatement.executeQuery();
			while (getTasksByClientIdResult.next()) {
				Task task = new Task();
				task.setId(getTasksByClientIdResult.getInt("taskId"));
				task.setTaskName(getTasksByClientIdResult.getString("taskName"));
				task.setTaskParameters(getTasksByClientIdResult.getString("taskParameter"));
				task.setTaskExePath(getTasksByClientIdResult.getString("taskPath"));
				task.setTaskState(getTasksByClientIdResult.getInt("taskState+0"));
				task.setUserId(getTasksByClientIdResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByClientIdResult.getInt("assignedNodeId"));

				tasksByClientId.add(task);
			}
			return tasksByClientId;
		} finally {
			getTasksByClientIdResult.close();
			getTasksByClientIdStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public Task GetTaskById(int taskId) {
		Connection conn = null;
		PreparedStatement getTasksByTaskIdStatement = null;
		ResultSet getTasksByTaskIdResult = null;
		Task task = new Task();

		try {
			conn = tdsDatabaseManager.getConnection();

			String GetTasksByTaskIdIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where taskId = ?";
			getTasksByTaskIdStatement = conn.prepareStatement(GetTasksByTaskIdIdQuery);
			getTasksByTaskIdStatement.setInt(1, taskId);

			getTasksByTaskIdResult = getTasksByTaskIdStatement.executeQuery();
			getTasksByTaskIdResult.next();
				
				task.setId(getTasksByTaskIdResult.getInt("taskId"));
				task.setTaskName(getTasksByTaskIdResult.getString("taskName"));
				task.setTaskParameters(getTasksByTaskIdResult.getString("taskParameter"));
				task.setTaskExePath(getTasksByTaskIdResult.getString("taskPath"));
				task.setTaskState(getTasksByTaskIdResult.getInt("taskState+0"));
				task.setUserId(getTasksByTaskIdResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByTaskIdResult.getInt("assignedNodeId"));

			return task;
		} finally {
			getTasksByTaskIdResult.close();
			getTasksByTaskIdStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Task> GetTasksByStatus(int status) {
		List<Task> tasksByStatus = new ArrayList<Task>();
		Connection conn = null;
		PreparedStatement getTasksByStatusStatement = null;
		ResultSet getTasksByStatusResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String GetTasksByClientIdIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where taskState = ?";
			getTasksByStatusStatement = conn.prepareStatement(GetTasksByClientIdIdQuery);
			getTasksByStatusStatement.setInt(1, status);

			getTasksByStatusResult = getTasksByStatusStatement.executeQuery();
			while (getTasksByStatusResult.next()) {
				Task task = new Task();
				task.setId(getTasksByStatusResult.getInt("taskId"));
				task.setTaskName(getTasksByStatusResult.getString("taskName"));
				task.setTaskParameters(getTasksByStatusResult.getString("taskParameter"));
				task.setTaskExePath(getTasksByStatusResult.getString("taskPath"));
				task.setTaskState(getTasksByStatusResult.getInt("taskState+0"));
				task.setUserId(getTasksByStatusResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByStatusResult.getInt("assignedNodeId"));

				tasksByStatus.add(task);
			}
			return tasksByStatus;
		} finally {
			getTasksByStatusResult.close();
			getTasksByStatusStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Task> GetTasksByNodeId(int nodeId) {
		List<Task> tasksByNodeId = new ArrayList<Task>();
		Connection conn = null;
		PreparedStatement getTasksByNodeIdStatement = null;
		ResultSet getTasksByNodeIdResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String GetTasksByNodeIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where assignedNodeId = ?";
			getTasksByNodeIdStatement = conn.prepareStatement(GetTasksByNodeIdQuery);
			getTasksByNodeIdStatement.setInt(1, nodeId);

			getTasksByNodeIdResult = getTasksByNodeIdStatement.executeQuery();
			while (getTasksByNodeIdResult.next()) {
				Task task = new Task();
				task.setId(getTasksByNodeIdResult.getInt("taskId"));
				task.setTaskName(getTasksByNodeIdResult.getString("taskName"));
				task.setTaskParameters(getTasksByNodeIdResult.getString("taskParameter"));
				task.setTaskExePath(getTasksByNodeIdResult.getString("taskPath"));
				task.setTaskState(getTasksByNodeIdResult.getInt("taskState+0"));
				task.setUserId(getTasksByNodeIdResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByNodeIdResult.getInt("assignedNodeId"));

				tasksByNodeId.add(task);
			}
			return tasksByNodeId;
		} finally {
			getTasksByNodeIdResult.close();
			getTasksByNodeIdStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void AssignNode(int nodeID, int taskId) throws Exception {
		Connection conn = null;
		PreparedStatement assignNodeStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String updateTaskStatusQuery = "UPDATE `tds`.`task` SET `assignedNodeId` = ? WHERE (`taskId` = ?)";

			assignNodeStatement = conn.prepareStatement(updateTaskStatusQuery);
			assignNodeStatement.setInt(1, nodeID);
			assignNodeStatement.setInt(2, taskId);

			int rowsAffected = assignNodeStatement.executeUpdate();

			if (rowsAffected == 1) {
				// conn.commit();
			} else {
				// log(no row affected)
			}

		} finally {
			assignNodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}

	}

}
