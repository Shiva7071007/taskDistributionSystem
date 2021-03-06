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
import com.itt.tds.core.Task;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class TDSTaskRepository implements TaskRepository {
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public int Add(Task taskInstance) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int taskId = 0;
		String taskName = taskInstance.getTaskName();
		String taskParameters = Utility.stringArrayListToJSONArray(taskInstance.getTaskParameters());
		String taskPath = taskInstance.getTaskExePath();
		int taskState = taskInstance.getTaskState();
		int userID = taskInstance.getUserId();

		String insertTaskQuery = "INSERT INTO `tds`.`task` (`taskName`, `taskParameter`, `taskPath`, `taskState`, `userID`) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement insertTaskStatement = null;
		ResultSet taskIdSet = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			insertTaskStatement = conn.prepareStatement(insertTaskQuery, Statement.RETURN_GENERATED_KEYS);
			insertTaskStatement.setString(1, taskName);
			insertTaskStatement.setString(2, taskParameters);
			insertTaskStatement.setString(3, taskPath);
			insertTaskStatement.setInt(4, taskState);
			insertTaskStatement.setInt(5, userID);

			int rowsAffected = insertTaskStatement.executeUpdate();
			logger.info("rows affected after inserting task into the database :" + rowsAffected);

			taskIdSet = insertTaskStatement.getGeneratedKeys();
			taskIdSet.next();
			taskId = taskIdSet.getInt(1);

			return taskId;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to insert task into the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(taskIdSet);
			tdsDatabaseManager.closePreparedStatement(insertTaskStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Delete(int taskId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		String deleteTaskQuery = "DELETE FROM `tds`.`task` WHERE (`taskId` = ?)";
		Connection conn = null;
		PreparedStatement deleteTaskStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			deleteTaskStatement = conn.prepareStatement(deleteTaskQuery);
			deleteTaskStatement.setInt(1, taskId);
			int rowsAffected = deleteTaskStatement.executeUpdate();
			logger.info("rows affected after deleting task :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to delete task from the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(deleteTaskStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Modify(Task taskInstance) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int taskId = taskInstance.getId();
		String newTaskName = taskInstance.getTaskName();
		String newTaskParameters = Utility.stringArrayListToJSONArray(taskInstance.getTaskParameters());
		String newTaskPath = taskInstance.getTaskExePath();
		int newTaskState = taskInstance.getTaskState();
		int newUserID = taskInstance.getUserId();
		int newAssignedNodeId = taskInstance.getAssingedNodeId();

		String modifyTaskQuery = "UPDATE `tds`.`task` SET `taskName` = ?, `taskParameter` = ?, `taskPath` = ?, `taskState` = ?, `userID` = ?, `assignedNodeId` = ? WHERE (`taskId` = ?)";
		Connection conn = null;
		PreparedStatement modifyTaskStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			modifyTaskStatement = conn.prepareStatement(modifyTaskQuery);
			modifyTaskStatement.setString(1, newTaskName);
			modifyTaskStatement.setString(2, newTaskParameters);
			modifyTaskStatement.setString(3, newTaskPath);
			modifyTaskStatement.setInt(4, newTaskState);
			modifyTaskStatement.setInt(5, newUserID);
			modifyTaskStatement.setInt(6, newAssignedNodeId);
			modifyTaskStatement.setInt(7, taskId);

			int rowsAffected = modifyTaskStatement.executeUpdate();
			logger.info("rows affected after modifying task :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to modify task into the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(modifyTaskStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void SetTaskStatus(int taskId, int status) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		String updateTaskStatusQuery = "UPDATE `tds`.`task` SET `taskState` = ? WHERE (`taskId` = ?)";
		Connection conn = null;
		PreparedStatement updateTaskStatusQueryStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			updateTaskStatusQueryStatement = conn.prepareStatement(updateTaskStatusQuery);
			updateTaskStatusQueryStatement.setInt(1, status);
			updateTaskStatusQueryStatement.setInt(2, taskId);

			int rowsAffected = updateTaskStatusQueryStatement.executeUpdate();
			logger.info("rows affected after updating task status :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to set status of the task in the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(updateTaskStatusQueryStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Task> GetTasksByClientId(int clientId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		List<Task> tasksByClientId = new ArrayList<Task>();
		Connection conn = null;
		PreparedStatement getTasksByClientIdStatement = null;
		ResultSet getTasksByClientIdResult = null;
		String GetTasksByClientIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where userID = ?";

		try {
			conn = tdsDatabaseManager.getConnection();

			getTasksByClientIdStatement = conn.prepareStatement(GetTasksByClientIdQuery);
			getTasksByClientIdStatement.setInt(1, clientId);

			getTasksByClientIdResult = getTasksByClientIdStatement.executeQuery();
			while (getTasksByClientIdResult.next()) {
				Task task = new Task();
				task.setId(getTasksByClientIdResult.getInt("taskId"));
				task.setTaskName(getTasksByClientIdResult.getString("taskName"));
				task.setTaskParameters(
						Utility.jsonArrayToStringArrayList(getTasksByClientIdResult.getString("taskParameter")));
				task.setTaskExePath(getTasksByClientIdResult.getString("taskPath"));
				task.setTaskState(getTasksByClientIdResult.getInt("taskState+0"));
				task.setUserId(getTasksByClientIdResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByClientIdResult.getInt("assignedNodeId"));

				tasksByClientId.add(task);
			}
			return tasksByClientId;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get tasks by client id from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(getTasksByClientIdResult);
			tdsDatabaseManager.closePreparedStatement(getTasksByClientIdStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public Task GetTaskById(int taskId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement getTasksByTaskIdStatement = null;
		ResultSet getTasksByTaskIdResult = null;
		Task task = null;
		String GetTasksByTaskIdIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where taskId = ?";

		try {
			task = new Task();
			conn = tdsDatabaseManager.getConnection();

			getTasksByTaskIdStatement = conn.prepareStatement(GetTasksByTaskIdIdQuery);
			getTasksByTaskIdStatement.setInt(1, taskId);

			getTasksByTaskIdResult = getTasksByTaskIdStatement.executeQuery();
			getTasksByTaskIdResult.next();

			task.setId(getTasksByTaskIdResult.getInt("taskId"));
			task.setTaskName(getTasksByTaskIdResult.getString("taskName"));
			task.setTaskParameters(
					Utility.jsonArrayToStringArrayList(getTasksByTaskIdResult.getString("taskParameter")));
			task.setTaskExePath(getTasksByTaskIdResult.getString("taskPath"));
			task.setTaskState(getTasksByTaskIdResult.getInt("taskState+0"));
			task.setUserId(getTasksByTaskIdResult.getInt("userID"));
			task.setAssignedNodeId(getTasksByTaskIdResult.getInt("assignedNodeId"));

			return task;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get task by id from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(getTasksByTaskIdResult);
			tdsDatabaseManager.closePreparedStatement(getTasksByTaskIdStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Task> GetTasksByStatus(int status) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		List<Task> tasksByStatus = new ArrayList<Task>();
		Connection conn = null;
		PreparedStatement getTasksByStatusStatement = null;
		ResultSet getTasksByStatusResult = null;
		String GetTasksByClientIdIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where taskState = ?";

		try {
			conn = tdsDatabaseManager.getConnection();

			getTasksByStatusStatement = conn.prepareStatement(GetTasksByClientIdIdQuery);
			getTasksByStatusStatement.setInt(1, status);

			getTasksByStatusResult = getTasksByStatusStatement.executeQuery();
			while (getTasksByStatusResult.next()) {
				Task task = new Task();
				task.setId(getTasksByStatusResult.getInt("taskId"));
				task.setTaskName(getTasksByStatusResult.getString("taskName"));
				task.setTaskParameters(
						Utility.jsonArrayToStringArrayList(getTasksByStatusResult.getString("taskParameter")));
				task.setTaskExePath(getTasksByStatusResult.getString("taskPath"));
				task.setTaskState(getTasksByStatusResult.getInt("taskState+0"));
				task.setUserId(getTasksByStatusResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByStatusResult.getInt("assignedNodeId"));

				tasksByStatus.add(task);
			}
			return tasksByStatus;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get task by status from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(getTasksByStatusResult);
			tdsDatabaseManager.closePreparedStatement(getTasksByStatusStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<Task> GetTasksByNodeId(int nodeId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		List<Task> tasksByNodeId = new ArrayList<Task>();
		Connection conn = null;
		PreparedStatement getTasksByNodeIdStatement = null;
		ResultSet getTasksByNodeIdResult = null;
		String GetTasksByNodeIdQuery = "SELECT taskId, taskName, taskParameter, taskPath, taskState+0, userID, assignedNodeId FROM tds.task where assignedNodeId = ?";

		try {
			conn = tdsDatabaseManager.getConnection();

			getTasksByNodeIdStatement = conn.prepareStatement(GetTasksByNodeIdQuery);
			getTasksByNodeIdStatement.setInt(1, nodeId);
			getTasksByNodeIdResult = getTasksByNodeIdStatement.executeQuery();

			while (getTasksByNodeIdResult.next()) {
				Task task = new Task();
				task.setId(getTasksByNodeIdResult.getInt("taskId"));
				task.setTaskName(getTasksByNodeIdResult.getString("taskName"));
				task.setTaskParameters(
						Utility.jsonArrayToStringArrayList(getTasksByNodeIdResult.getString("taskParameter")));
				task.setTaskExePath(getTasksByNodeIdResult.getString("taskPath"));
				task.setTaskState(getTasksByNodeIdResult.getInt("taskState+0"));
				task.setUserId(getTasksByNodeIdResult.getInt("userID"));
				task.setAssignedNodeId(getTasksByNodeIdResult.getInt("assignedNodeId"));

				tasksByNodeId.add(task);
			}
			return tasksByNodeId;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get task by node id from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(getTasksByNodeIdResult);
			tdsDatabaseManager.closePreparedStatement(getTasksByNodeIdStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void AssignNode(int nodeID, int taskId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement assignNodeStatement = null;
		String updateTaskStatusQuery = "UPDATE `tds`.`task` SET `assignedNodeId` = ? WHERE (`taskId` = ?)";

		try {
			conn = tdsDatabaseManager.getConnection();

			assignNodeStatement = conn.prepareStatement(updateTaskStatusQuery);
			assignNodeStatement.setInt(1, nodeID);
			assignNodeStatement.setInt(2, taskId);

			int rowsAffected = assignNodeStatement.executeUpdate();
			logger.info("rows affected after assigning node to task :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to assign node to task in the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(assignNodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}
}
