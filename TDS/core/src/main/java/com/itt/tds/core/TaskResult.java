package com.itt.tds.core;


/**
 * 
 */
public class TaskResult {

    /**
     * Default constructor
     */
    public TaskResult() {
    }

    public int errorCode;

    public String errorMessage;

    public byte[] resultBuffer;

    public int taskId;

    public TaskOutcome taskOutcome;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public byte[] getResultBuffer() {
		return resultBuffer;
	}

	public void setResultBuffer(byte[] resultBuffer) {
		this.resultBuffer = resultBuffer;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public TaskOutcome getTaskOutcome() {
		return taskOutcome;
	}

	public void setTaskOutcome(TaskOutcome taskOutcome) {
		this.taskOutcome = taskOutcome;
	}

}