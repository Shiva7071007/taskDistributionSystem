package com.itt.tds.coordinator.db.repository;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import com.itt.tds.core.TaskOutcome;
import com.itt.tds.core.TaskResult;
import com.mysql.cj.xdevapi.InsertStatement;

public class TDSTaskResultRepository implements TaskResultRepository {

	@Override
	public void Add(TaskResult taskresultInstance) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		int taskId = taskresultInstance.getTaskId();
		int taskOutcome = taskresultInstance.getTaskOutcome();
		int errCode = taskresultInstance.getErrorCode();
		String errMsg = taskresultInstance.getErrorMessage();
		byte[] resultBuffer = taskresultInstance.getResultBuffer();

		Connection conn = null;
		PreparedStatement insertTaskResultement = null;
		try {
			conn = tdsDatabaseManager.getConnection();

			String insertTaskResultQuery = "INSERT INTO `tds`.`taskresult` (`taskId`, `taskOutcome`, `taskErrorCode`, `taskErrorMsg`, `taskResultBuffer`) VALUES (?, ?, ?, ?, ?)";

			insertTaskResultement = conn.prepareStatement(insertTaskResultQuery);
			insertTaskResultement.setInt(1, taskId);
			insertTaskResultement.setInt(2, taskOutcome);
			insertTaskResultement.setInt(3, errCode);
			insertTaskResultement.setString(4, errMsg);
			insertTaskResultement.setBinaryStream(5, new ByteArrayInputStream(resultBuffer), resultBuffer.length);

			int rowsAffected = insertTaskResultement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			insertTaskResultement.close();
			conn.close();
		}

	}

	@Override
	public void Delete(int taskId) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		Connection conn = null;
		PreparedStatement deleteTaskResultStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String deleteTaskResultQuery = "DELETE FROM `tds`.`taskresult` WHERE (`taskId` = ?)";
			deleteTaskResultStatement = conn.prepareStatement(deleteTaskResultQuery);
			deleteTaskResultStatement.setInt(1, taskId);
			deleteTaskResultStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			deleteTaskResultStatement.clearBatch();
			conn.close();
		}
	}

	@Override
	public void Modify(TaskResult taskresultInstance) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();

		int taskId = taskresultInstance.getTaskId();
		int newTaskOutcome = taskresultInstance.getTaskOutcome();
		int newErrCode = taskresultInstance.getErrorCode();
		String newErrMsg = taskresultInstance.getErrorMessage();
		byte[] newResultBuffer = taskresultInstance.getResultBuffer();

		Connection conn = null;
		PreparedStatement modifyTaskResultStatement = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String modifyTaskResultQuery = "UPDATE `tds`.`taskresult` SET `taskOutcome` = ?, `taskErrorCode` = ?, `taskErrorMsg` = ?, `taskResultBuffer` = ? WHERE (`taskId` = ?)";
			modifyTaskResultStatement = conn.prepareStatement(modifyTaskResultQuery);
			modifyTaskResultStatement.setInt(1, newTaskOutcome);
			modifyTaskResultStatement.setInt(2, newErrCode);
			modifyTaskResultStatement.setString(3, newErrMsg);
			modifyTaskResultStatement.setBinaryStream(4, new ByteArrayInputStream(newResultBuffer),
					newResultBuffer.length);
			modifyTaskResultStatement.setInt(5, taskId);
			modifyTaskResultStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			modifyTaskResultStatement.clearBatch();
			conn.close();
		}
	}

	@Override
	public TaskResult getTaskResultByTaskId(int taskId) throws Exception {
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement getTaskResultByTaskIdStatement = null;
		ResultSet getTaskResultByTaskIdResult = null;
		TaskResult taskResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();
			String getTaskResultByTaskIdQuery = "SELECT taskId, taskOutcome+0, taskErrorCode, taskErrorMsg, taskResultBuffer FROM tds.taskresult where taskId = ?";

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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getTaskResultByTaskIdResult.close();
			getTaskResultByTaskIdStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
		return taskResult;
	}

	@Override
	public List<TaskResult> getTaskResultByErrCode(int errorCode) throws Exception {
		List<TaskResult> taskResultByErrCodeList = new ArrayList<TaskResult>();
		TDSDatabaseManager tdsDatabaseManager = TDSDatabaseManager.getInstance();
		Connection conn = null;
		PreparedStatement getTaskResultByErrCodeStatement = null;
		ResultSet getTaskResultByErrCodeResult = null;

		try {
			conn = tdsDatabaseManager.getConnection();

			String getTaskResultByTaskIdQuery = "SELECT taskId, taskOutcome+0, taskErrorCode, taskErrorMsg, taskResultBuffer FROM tds.taskresult where taskErrorCode = ?";

			getTaskResultByErrCodeStatement = conn.prepareStatement(getTaskResultByTaskIdQuery);
			getTaskResultByErrCodeStatement.setInt(1, errorCode);
			getTaskResultByErrCodeResult = getTaskResultByErrCodeStatement.executeQuery();
			
			while(getTaskResultByErrCodeResult.next()) {
				TaskResult taskResult = new TaskResult();
				taskResult.setTaskId(getTaskResultByErrCodeResult.getInt("taskId"));
				taskResult.setTaskOutcome(getTaskResultByErrCodeResult.getInt("taskOutcome"));
				taskResult.setErrorCode(getTaskResultByErrCodeResult.getInt("taskErrorCode"));
				taskResult.setErrorMessage(getTaskResultByErrCodeResult.getString("taskErrorMsg"));
				Blob taskResultBlob = getTaskResultByErrCodeResult.getBlob("taskResultBuffer");
				taskResult.setResultBuffer(taskResultBlob.getBytes(1L, (int) taskResultBlob.length()));
				
				taskResultByErrCodeList.add(taskResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getTaskResultByErrCodeResult.close();
			getTaskResultByErrCodeStatement.close();
			tdsDatabaseManager.closeConnection(conn);
		}
		return taskResultByErrCodeList;
	}

}
