package com.itt.tds.exceptions;

import org.apache.log4j.Logger;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

@SuppressWarnings("serial")
public class CoordinatorConfigurationException extends RuntimeException {
	static Logger logger = new TDSLogger().getLogger();

	public CoordinatorConfigurationException(TDSError unableToFindConfig, Throwable cause) {
		logger.error(unableToFindConfig.toString());
		logger.debug("Thrown by: " + cause.getClass().getSimpleName());
		logger.trace(cause.getMessage());
	}
}
