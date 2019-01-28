package com.itt.tds.core;

import java.util.ArrayList;

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
    private ArrayList<String> taskParameters;

    /**
     * 
     */
    private String taskExePath;

    /**
     * 
     */
    private int taskState;

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
    public void setTaskState(int taskState) {
       this.taskState = taskState;
    }

    /**
     * @return
     */
    public int getTaskState() {
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
    public void setTaskParameters(ArrayList<String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    /**
     * @return
     */
    public ArrayList<String> getTaskParameters() {
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
}