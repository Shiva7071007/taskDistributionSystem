package com.itt.tds.coordinator;

import com.itt.tds.comm.TDSRequest;

class RequestDispatcher {
	public static TDSController getController(TDSRequest request) throws Exception {
		if (request.getMethod().startsWith("node-")) {
			return new NodeController();
		} else if (request.getMethod().startsWith("client-")) {
			return new ClientController();
		} else {
			throw new Exception("Invalid getMethod");
		}
	}
}
