package com.itt.tds.client;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import com.itt.tds.comm.TDSRequest;

public class Client {
	
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	
	protected static TDSRequest prepareClientRequest () {
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
	
	protected static void setClientLogLevel () {
		ClientConfiguration clientCfg = ClientConfiguration.getInstance();
		Level logLevel = Level.toLevel(clientCfg.getLogLevel());
		LogManager.getRootLogger().setLevel(logLevel);
	}
}
