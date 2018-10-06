package com.itt.tds.coordinator;

import java.net.ServerSocket;
import java.util.*;

/**
 * 
 */
public class CommunicationManager {

    /**
     * Default constructor
     */
    public CommunicationManager() {
    }

    /**
     * 
     */
    public ServerSocket serverSocketForClient;

    /**
     * 
     */
    public ServerSocket serverSocketForNode;

    /**
     * @return
     */
    public ClientRequest waitForClient() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public NodeRequest waitForNode() {
        // TODO implement here
        return null;
    }

}