package com.itt.tds.TDSExceptions.RuntimeExceptions;

import org.apache.log4j.Logger;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class FatalException extends RuntimeException{
	
	private static final long serialVersionUID = 2656905202386533752L;
	static Logger logger = new TDSLogger().getLogger();
	
	public FatalException(String message) {
        logger.error(message);
    }
	
	public FatalException(TDSError errorCode) {
        logger.error(errorCode.toString());
    }

    public FatalException(String message, Throwable cause) {
    	logger.error(message, cause);
    }

    public FatalException(TDSError errorCode, Throwable cause) {
    	logger.error(errorCode.toString(), cause);
    }

}
