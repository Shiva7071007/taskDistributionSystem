package com.itt.tds.TDSExceptions;

import org.apache.log4j.Logger;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class TDSExceptions extends Exception {

	private static final long serialVersionUID = 985186607856375650L;
	static Logger logger = new TDSLogger().getLogger();
	
	public TDSExceptions(String message) {
        logger.error(message);
    }
	
	public TDSExceptions(TDSError errorCode) {
        logger.error(errorCode.toString());
    }

    public TDSExceptions(String message, Throwable cause) {
    	logger.error(message, cause);
    }

    public TDSExceptions(TDSError errorCode, Throwable cause) {
    	logger.error(errorCode.toString(), cause);
    }
}
