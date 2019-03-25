package com.itt.tds.TDSExceptions;

import org.apache.log4j.Logger;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class TDSException extends Exception {

	private static final long serialVersionUID = 985186607856375650L;
	static Logger logger = new TDSLogger().getLogger();
	
	public TDSException(String message) {
        logger.error(message);
    }
	
	public TDSException(TDSError errorCode) {
        logger.error(errorCode.toString());
    }

    public TDSException(String message, Throwable cause) {
    	logger.error(message, cause);
    }

    public TDSException(TDSError errorCode, Throwable cause) {
    	logger.error(errorCode.toString(), cause);
    }
}
