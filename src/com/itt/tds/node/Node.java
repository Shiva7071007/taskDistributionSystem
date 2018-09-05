package com.itt.tds.node;

import java.net.Socket;
import java.util.*;

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
    private String hostName;

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

}