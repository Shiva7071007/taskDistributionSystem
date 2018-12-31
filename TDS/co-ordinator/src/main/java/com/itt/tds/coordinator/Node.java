package com.itt.tds.coordinator;

import java.net.Socket;
import com.itt.tds.core.NodeState;
import com.itt.tds.core.Task;

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
    private Socket socket;

    /**
     * 
     */
    private NodeState nodeStatus;

    /**
     * @param task
     */
    public void sendTask(Task task) {
        // TODO implement here
    }

}