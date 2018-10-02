package com.itt.tds.core;

/**
 * 
 */
public class Task {

    /**
     * Default constructor
     */
    public Task() {
    }

    /**
     * 
     */
    private String taskName;

    /**
     * 
     */
    private String taskParameters;

    /**
     * 
     */
    private String taskExePath;

    /**
     * 
     */
    private TaskState taskState;

    /**
     * 
     */
    private TaskResult taskResult;

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private int userId;

    /**
     * 
     */
    private int assignedNodeId;

    /**
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskExePath
     */
    public void setTaskExePath(String taskExePath) {
        this.taskExePath = taskExePath;
    }

    /**
     * @return
     */
    public String getTaskExePath() {
        return taskExePath;
    }

    /**
     * @param taskState
     */
    public void setTaskState(TaskState taskState) {
       this.taskState = taskState;
    }

    /**
     * @return
     */
    public TaskState getTaskState() {
        return taskState;
    }

    /**
     * @param taskResult
     */
    public void setTaskResult(TaskResult taskResult) {
    	this.taskResult = taskResult;
    }

    /**
     * @return
     */
    public TaskResult getTaskResult() {
        return taskResult;
    }

    /**
     * @param id
     */
    public void setId(int id) {
    	this.id = id;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param taskParamaters
     */
    public void setTaskParameters(String taskParameters) {
        this.taskParameters = taskParameters;
    }

    /**
     * @return
     */
    public String getTaskParameters() {
        return taskParameters;
    }

    /**
     * @param userId
     */
    public void setUserId(int userId) {
    	this.userId = userId;
    }

    /**
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return
     */
    public int getAssingedNodeId() {
        return assignedNodeId;
    }

    /**
     * @return
     */
    public void setAssignedNodeId(int assignedNodeId) {
    	this.assignedNodeId = assignedNodeId;
    }

}