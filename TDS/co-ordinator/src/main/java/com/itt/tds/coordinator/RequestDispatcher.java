package com.itt.tds.coordinator;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.logging.TDSLogger;

class RequestDispatcher {
	static Logger logger = new TDSLogger().getLogger();
	
	public static TDSController getController(TDSRequest request) throws Exception {
		if (request.getMethod().startsWith("node-")) {
			logger.debug("returning a node controller");
			return new NodeController();
		} else if (request.getMethod().startsWith("client-")) {
			logger.debug("returning a client controller");
			return new ClientController();
		} else {
			throw new Exception("Invalid getMethod");
		}
	}
}
