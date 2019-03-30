package com.itt.tds.coordinator;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.TDSProtocolSerializationException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.logging.TDSLogger;

class RequestDispatcher {
	private static final String CLIENT = "client-";
	private static final String NODE = "node-";
	static Logger logger = new TDSLogger().getLogger();

	public static TDSController getController(TDSRequest request) throws TDSProtocolSerializationException {
		if (request.getMethod().startsWith(NODE)) {
			logger.debug("returning a node controller");
			return new NodeController();
		}
		
		if (request.getMethod().startsWith(CLIENT)) {
			logger.debug("returning a client controller");
			return new ClientController();
		}
		
		throw new TDSProtocolSerializationException("invalid request method got in request : " + request.getMethod());
	}
}
