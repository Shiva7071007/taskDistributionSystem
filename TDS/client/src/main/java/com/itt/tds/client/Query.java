package com.itt.tds.client;

import org.apache.log4j.Logger;

import com.itt.tds.comm.DestinationComManager;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.logging.TDSLogger;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "query", mixinStandardHelpOptions = true, header = "get the current status of task by task ID")
public class Query implements Runnable {
	@Parameters(index = "0", description = "task-id of the task got from server while queuing")
	int taskId;

	private static final String CLIENT_QUERY_TASK = "client-queryTask";
	private static final String TASK_ID = "taskId";
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String TASK_STATUS = "taskStatus";
	private static final String SEPARATOR = " : ";
	private static final String STATUS = "Status";
	private static final String PENDING = "Pending";
	private static final String EXECUTING = "Executing";
	private static final String COMPLETED = "Completed";
	private static final String ERROR_CODE = "Error-code";
	private static final String SUCCESS = "SUCCESS";
	

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		ClientConfiguration clientCfg = ClientConfiguration.getInstance();

		TDSRequest request = new TDSRequest();
		request.setProtocolVersion(clientCfg.getProtocolVersion());
		request.setProtocolFormat(clientCfg.getProtocolFormat());
		request.setSourceIp(clientCfg.getSourceIp());
		request.setSourcePort(clientCfg.getSourcePort());
		request.setDestIp(clientCfg.getDestinationIp());
		request.setDestPort(clientCfg.getDestinationPort());
		request.setMethod(CLIENT_QUERY_TASK);
		request.setParameters(TASK_ID, Integer.toString(taskId));
		request.setParameters(HOSTNAME, clientCfg.getHostName());
		request.setParameters(USERNAME, clientCfg.getUserName());

		TDSResponse response = DestinationComManager.getResponse(request);

		if (response.getStatus().equalsIgnoreCase(SUCCESS)) {
			String statusCode = response.getValue(TASK_STATUS);
			logger.info(STATUS + SEPARATOR + getStatusValueFromCode(statusCode));
		} else {
			String errorCode = response.getErrorCode();
			String errorMsg = response.getErrorMessage();
			logger.error(ERROR_CODE + SEPARATOR + errorCode + " " + errorMsg);
		}
	}

	private String getStatusValueFromCode(String statusCode) {
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
