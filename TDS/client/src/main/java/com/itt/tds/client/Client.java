package com.itt.tds.client;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.TDSExceptions.RuntimeExceptions.CommunicationException;
import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.logging.TDSLogger;

public class Client {

	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String PENDING = "Pending";
	private static final String EXECUTING = "Executing";
	private static final String COMPLETED = "Completed";
	
	protected static final int TIMEOUT = 5;
	protected static final String SUCCESS = "SUCCESS";
	protected static final String TASK_ID = "taskId";
	protected static final String SEPARATOR = " : ";
	
	static Logger logger = new TDSLogger().getLogger();

	protected static TDSRequest prepareClientRequest() {
		ClientConfiguration clientCfg = ClientConfiguration.getInstance();

		TDSRequest request = new TDSRequest();
		request.setProtocolVersion(clientCfg.getProtocolVersion());
		request.setProtocolFormat(clientCfg.getProtocolFormat());
		request.setSourceIp(clientCfg.getSourceIp());
		request.setSourcePort(clientCfg.getSourcePort());
		request.setDestIp(clientCfg.getDestinationIp());
		request.setDestPort(clientCfg.getDestinationPort());
		request.setParameters(HOSTNAME, clientCfg.getHostName());
		request.setParameters(USERNAME, clientCfg.getUserName());

		return request;
	}

	protected static void setClientLogLevel() {
		ClientConfiguration clientCfg = ClientConfiguration.getInstance();
		Level logLevel = Level.toLevel(clientCfg.getLogLevel());
		LogManager.getRootLogger().setLevel(logLevel);
	}

	protected static TDSResponse getResponse(TDSRequest request, int timeout) {
		TDSResponse response = null;

		try {
			response = TDSClient.getResponse(request, timeout);
		} catch (ServerCommunicationException e) {
			throw new CommunicationException("Something went wrong while communicating with server. Please refer logs for more details", e);
		}
		return response;
	}
	
	protected String getStatusValueFromCode(String statusCode) {
		switch (statusCode) {
		case "1":
			return PENDING;
		case "2":
			return EXECUTING;
		case "3":
			return COMPLETED;
		}
		return statusCode;
	}
}
