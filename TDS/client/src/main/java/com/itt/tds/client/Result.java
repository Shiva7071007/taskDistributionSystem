package com.itt.tds.client;

import static picocli.CommandLine.*;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.logging.TDSLogger;

import picocli.CommandLine.Parameters;

@Command(name = "result", mixinStandardHelpOptions = true, header = "get the result for passed task ID")
public class Result implements Runnable {
	@Parameters(index = "0", description = "task-id of the task got from server while queuing")
	int taskId;

	private static final String CLIENT_RESULT_TASK = "client-resultTask";
	private static final String TASK_ID = "taskId";
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String TASK_STATUS = "taskStatus";
	private static final String TASK_RESULT = "taskResult";

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
		request.setMethod(CLIENT_RESULT_TASK);
		request.setParameters(TASK_ID, Integer.toString(taskId));
		request.setParameters(HOSTNAME, clientCfg.getHostName());
		request.setParameters(USERNAME, clientCfg.getUserName());

		TDSResponse response = Server.getResponse(request);

		if (response.getStatus().equalsIgnoreCase("SUCCESS")) {

			String status = response.getValue(TASK_STATUS);
			if (status.equalsIgnoreCase("completed")) {
				String result = response.getValue(TASK_RESULT);
				logger.info(result);
			} else
				logger.info("Status : " + status);

		} else {
			String errorCode = response.getErrorCode();
			String errorMsg = response.getErrorMessage();
			logger.error("Error-code : " + errorCode + " " + errorMsg);
		}
	}
}
