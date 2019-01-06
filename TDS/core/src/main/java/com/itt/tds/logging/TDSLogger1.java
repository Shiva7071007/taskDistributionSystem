package com.itt.tds.logging;


import java.util.logging.Level;
import java.util.logging.Logger;

public class TDSLogger1 implements Logging {
	
	private Logger tdsLogger;
	
	public TDSLogger1(Class<?> loggerClass) {
		tdsLogger = Logger.getLogger(loggerClass.getName());
    }

	@Override
	public void logWarn(String className, String methodName, String message) {
		if(tdsLogger.isLoggable(Level.WARNING)) {
			tdsLogger.log(Level.WARNING, message);
		}
	}

	@Override
	public void logWarn(String className, String methodName, String message, Exception ex) {
		if(tdsLogger.isLoggable(Level.WARNING)) {
			tdsLogger.log(Level.WARNING, message);
		}
	}

	@Override
	public void logError(String className, String methodName, String message) {
		if(tdsLogger.isLoggable(Level.SEVERE)) {
			tdsLogger.log(Level.SEVERE, message);
		}
	}

	@Override
	public void logError(String className, String methodName, String message, Exception ex) {
		if(tdsLogger.isLoggable(Level.SEVERE)) {
			tdsLogger.log(Level.SEVERE, message);
		}
	}

	@Override
	public void logInfo(String className, String methodName, String message) {
		if(tdsLogger.isLoggable(Level.INFO)) {
			tdsLogger.log(Level.INFO, message);
		}
	}

	@Override
	public void logInfo(String className, String methodName, String message, Exception ex) {
		if(tdsLogger.isLoggable(Level.INFO)) {
			tdsLogger.log(Level.INFO, message);
		}
	}

	@Override
	public void logDebug(String className, String methodName, String message) {
		if(tdsLogger.isLoggable(Level.FINE)) {
			tdsLogger.log(Level.FINE, message);
		}
	}

	@Override
	public void logDebug(String className, String methodName, String message, Exception ex) {
		if(tdsLogger.isLoggable(Level.FINE)) {
			tdsLogger.log(Level.FINE, message);
		}
	}

}
