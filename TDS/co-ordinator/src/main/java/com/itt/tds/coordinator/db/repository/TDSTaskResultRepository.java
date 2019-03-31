package com.itt.tds.coordinator.db.repository;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.DatabaseConnectionException;
import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.TaskResult;
import com.itt.tds.logging.TDSLogger;

public class TDSTaskResultRepository implements TaskResultRepository {
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void Add(TaskResult taskresultInstance) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int taskId = taskresultInstance.getTaskId();
		int taskOutcome = taskresultInstance.getTaskOutcome();
		int errCode = taskresultInstance.getErrorCode();
		String errMsg = taskresultInstance.getErrorMessage();
		byte[] resultBuffer = taskresultInstance.getResultBuffer();

		String insertTaskResultQuery = "INSERT INTO `tds`.`taskresult` (`taskId`, `taskOutcome`, `taskErrorCode`, `taskErrorMsg`, `taskResultBuffer`) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement insertTaskResultement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			insertTaskResultement = conn.prepareStatement(insertTaskResultQuery);
			insertTaskResultement.setInt(1, taskId);
			insertTaskResultement.setInt(2, taskOutcome);
			insertTaskResultement.setInt(3, errCode);
			insertTaskResultement.setString(4, errMsg);
			insertTaskResultement.setBinaryStream(5, new ByteArrayInputStream(resultBuffer), resultBuffer.length);

			int rowsAffected = insertTaskResultement.executeUpdate();
			logger.debug("rows affected after inserting taskResult into the database :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to add taskResult into the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(insertTaskResultement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Delete(int taskId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		String deleteTaskResultQuery = "DELETE FROM `tds`.`taskresult` WHERE (`taskId` = ?)";
		Connection conn = null;
		PreparedStatement deleteTaskResultStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			deleteTaskResultStatement = conn.prepareStatement(deleteTaskResultQuery);
			deleteTaskResultStatement.setInt(1, taskId);

			int rowsAffected = deleteTaskResultStatement.executeUpdate();
			logger.debug("rows affected after deleteing taskResult from the database :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to delete taskResult by task id from the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(deleteTaskResultStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public void Modify(TaskResult taskresultInstance) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		int taskId = taskresultInstance.getTaskId();
		int newTaskOutcome = taskresultInstance.getTaskOutcome();
		int newErrCode = taskresultInstance.getErrorCode();
		String newErrMsg = taskresultInstance.getErrorMessage();
		byte[] newResultBuffer = taskresultInstance.getResultBuffer();

		String modifyTaskResultQuery = "UPDATE `tds`.`taskresult` SET `taskOutcome` = ?, `taskErrorCode` = ?, `taskErrorMsg` = ?, `taskResultBuffer` = ? WHERE (`taskId` = ?)";
		Connection conn = null;
		PreparedStatement modifyTaskResultStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			modifyTaskResultStatement = conn.prepareStatement(modifyTaskResultQuery);
			modifyTaskResultStatement.setInt(1, newTaskOutcome);
			modifyTaskResultStatement.setInt(2, newErrCode);
			modifyTaskResultStatement.setString(3, newErrMsg);
			modifyTaskResultStatement.setBinaryStream(4, new ByteArrayInputStream(newResultBuffer),
					newResultBuffer.length);
			modifyTaskResultStatement.setInt(5, taskId);

			int rowsAffected = modifyTaskResultStatement.executeUpdate();
			logger.debug("rows affected after deleteing taskResult from the database :" + rowsAffected);

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to modify taskResult in the table", e);
		} finally {
			tdsDatabaseManager.closePreparedStatement(modifyTaskResultStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public TaskResult getTaskResultByTaskId(int taskId) throws DatabaseTransactionException {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		String getTaskResultByTaskIdQuery = "SELECT taskId, taskOutcome+0, taskErrorCode, taskErrorMsg, taskResultBuffer FROM tds.taskresult where taskId = ?";
		Connection conn = null;
		PreparedStatement getTaskResultByTaskIdStatement = null;
		ResultSet getTaskResultByTaskIdResult = null;
		TaskResult taskResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			getTaskResultByTaskIdStatement = conn.prepareStatement(getTaskResultByTaskIdQuery);
			getTaskResultByTaskIdStatement.setInt(1, taskId);
			getTaskResultByTaskIdResult = getTaskResultByTaskIdStatement.executeQuery();

			if (getTaskResultByTaskIdResult.next()) {
				taskResult = new TaskResult();
				taskResult.setTaskId(getTaskResultByTaskIdResult.getInt("taskId"));
				taskResult.setTaskOutcome(getTaskResultByTaskIdResult.getInt("taskOutcome+0"));
				taskResult.setErrorCode(getTaskResultByTaskIdResult.getInt("taskErrorCode"));
				taskResult.setErrorMessage(getTaskResultByTaskIdResult.getString("taskErrorMsg"));
				Blob taskResultBlob = getTaskResultByTaskIdResult.getBlob("taskResultBuffer");
				taskResult.setResultBuffer(taskResultBlob.getBytes(1L, (int) taskResultBlob.length()));
			}

			return taskResult;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get taskResult by task id from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(getTaskResultByTaskIdResult);
			tdsDatabaseManager.closePreparedStatement(getTaskResultByTaskIdStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}

	@Override
	public List<TaskResult> getTaskResultByErrCode(int errorCode) throws DatabaseTransactionException {
		List<TaskResult> taskResultByErrCodeList = new ArrayList<TaskResult>();
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		String getTaskResultByTaskIdQuery = "SELECT taskId, taskOutcome+0, taskErrorCode, taskErrorMsg, taskResultBuffer FROM tds.taskresult where taskErrorCode = ?";
		Connection conn = null;
		PreparedStatement getTaskResultByErrCodeStatement = null;
		ResultSet getTaskResultByErrCodeResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			getTaskResultByErrCodeStatement = conn.prepareStatement(getTaskResultByTaskIdQuery);
			getTaskResultByErrCodeStatement.setInt(1, errorCode);
			getTaskResultByErrCodeResult = getTaskResultByErrCodeStatement.executeQuery();

			while (getTaskResultByErrCodeResult.next()) {
				TaskResult taskResult = new TaskResult();
				taskResult.setTaskId(getTaskResultByErrCodeResult.getInt("taskId"));
				taskResult.setTaskOutcome(getTaskResultByErrCodeResult.getInt("taskOutcome"));
				taskResult.setErrorCode(getTaskResultByErrCodeResult.getInt("taskErrorCode"));
				taskResult.setErrorMessage(getTaskResultByErrCodeResult.getString("taskErrorMsg"));
				Blob taskResultBlob = getTaskResultByErrCodeResult.getBlob("taskResultBuffer");
				taskResult.setResultBuffer(taskResultBlob.getBytes(1L, (int) taskResultBlob.length()));

				taskResultByErrCodeList.add(taskResult);
			}

			return taskResultByErrCodeList;

		} catch (SQLException | DatabaseConnectionException e) {
			throw new DatabaseTransactionException("failed to get taskResult by error code from the table", e);
		} finally {
			tdsDatabaseManager.closeResultSet(getTaskResultByErrCodeResult);
			tdsDatabaseManager.closePreparedStatement(getTaskResultByErrCodeStatement);
			tdsDatabaseManager.closeConnection(conn);
		}
	}
}
