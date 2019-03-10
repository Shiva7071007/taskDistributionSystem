package com.itt.tds.TDSExceptions;

import org.apache.log4j.Logger;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class TDSExceptions extends Exception {

	private static final long serialVersionUID = 985186607856375650L;
	static Logger logger = new TDSLogger().getLogger();
	private static final String THROWN_BY = "Thrown by : ";
	
	public TDSExceptions (TDSError error, Throwable cause) {
		logger.error(error.toString());
		logger.debug(THROWN_BY + cause.getClass().getSimpleName());
		logger.trace(cause.getMessage());
	}
}
