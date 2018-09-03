package com.itt.tds.core;

import java.util.*;

/**
 * 
 */
public class TaskResult {

    /**
     * Default constructor
     */
    public TaskResult() {
    }

    /**
     * 
     */
    public int errorCode;

    /**
     * 
     */
    public String errorMessage;

    /**
     * 
     */
    public Array<byte> resultBuffer;

    /**
     * 
     */
    public int taskId;

    /**
     * 
     */
    public TaskOutcome taskOutcome;

}