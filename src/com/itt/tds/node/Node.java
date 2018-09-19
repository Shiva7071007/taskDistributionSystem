package com.itt.tds.node;

import java.net.Socket;
import java.util.*;

import com.itt.tds.core.Task;
import com.itt.tds.core.TaskResult;

/**
 * 
 */
public class Node {

    /**
     * Default constructor
     */
    public Node() {
    }

    /**
     * 
     */
    private String iP;

    /**
     * 
     */
    private int port;

    /**
     * 
     */
    private int Status;

    /**
     * 
     */
    private Task currentTask;

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private Socket coordinatorSocket;

    /**
     * @param taskInstance
     */
    public void executeTask(Task taskInstance) {
        // TODO implement here
    }

    /**
     * @param result
     */
    public void postResults(TaskResult result) {
        // TODO implement here
    }

    /**
     * 
     */
    public void pingCoordinator() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Task waitForTask() {
        // TODO implement here
        return null;
    }

	public String getiP() {
		return iP;
	}

	public void setiP(String iP) {
		this.iP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}