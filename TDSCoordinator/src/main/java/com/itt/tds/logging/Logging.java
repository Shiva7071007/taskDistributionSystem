package com.itt.tds.logging;

import java.util.*;

/**
 * 
 */
public interface Logging {

    /**
     * @param className 
     * @param methodName 
     * @param message
     */
    public void logWarn(String className, String methodName, String message);

    /**
     * @param className 
     * @param methodName 
     * @param message 
     * @param ex
     */
    public void logWarn(String className, String methodName, String message, Exception ex);

    /**
     * @param className 
     * @param methodName 
     * @param message
     */
    public void logError(String className, String methodName, String message);

    /**
     * @param className 
     * @param methodName 
     * @param message 
     * @param ex
     */
    public void logError(String className, String methodName, String message, Exception ex);

    /**
     * @param className 
     * @param methodName 
     * @param message
     */
    public void logInfo(String className, String methodName, String message);

    /**
     * @param className 
     * @param methodName 
     * @param message 
     * @param ex
     */
    public void logInfo(String className, String methodName, String message, Exception ex);

    /**
     * @param className 
     * @param methodName 
     * @param message
     */
    public void logDebug(String className, String methodName, String message);

    /**
     * @param className 
     * @param methodName 
     * @param message 
     * @param ex
     */
    public void logDebug(String className, String methodName, String message, Exception ex);

}