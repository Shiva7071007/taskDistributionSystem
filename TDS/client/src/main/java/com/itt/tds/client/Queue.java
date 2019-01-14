package com.itt.tds.client;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

import picocli.CommandLine.*;
import picocli.CommandLine.Parameters;

@Command(name = "queue", mixinStandardHelpOptions = true, header = "add a program in queue for execution")
public class Queue implements Runnable {
	@Parameters(index = "0", description = "Executables files that needs to be sent to server")
	File task;

	@Parameters(index = "1..*", description = "parameters need to be passed with task")
	List<String> parameters;

	static Logger logger = new TDSLogger().getLogger();

	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "userName";
	private static final String CLIENT_QUEUE_TASK = "client-queueTask";
	private static final String TASK_NAME = "taskName";
	private static final String PARAMETERS = "parameters";
	private static final String TASK_STATUS = "taskStatus";
	private static final String TASK_ID = "taskId";

	@Override
	public void run() {

		if (!task.exists()) {
			logger.error("passed task: " + task.getName() + "doesn't exist");
			logger.error("please provide a correct task file address");
		} else {
			if (parameters == null) {
				logger.debug("no parameters");
				parameters = Collections.emptyList();
			}

			ClientConfiguration clientCfg = ClientConfiguration.getInstance();

			TDSRequest request = new TDSRequest();
			request.setProtocolVersion(clientCfg.getProtocolVersion());
			request.setProtocolFormat(clientCfg.getProtocolFormat());
			request.setSourceIp(clientCfg.getSourceIp());
			request.setSourcePort(clientCfg.getSourcePort());
			request.setDestIp(clientCfg.getDestinationIp());
			request.setDestPort(clientCfg.getDestinationPort());
			request.setMethod(CLIENT_QUEUE_TASK);
			request.setParameters(TASK_NAME, task.getName());
			request.setParameters(PARAMETERS, parameters.toString());
			request.setParameters(HOSTNAME, clientCfg.getHostName());
			request.setParameters(USERNAME, clientCfg.getUserName());
			request.setData(Utility.convertToByte(task));

			TDSResponse response = Server.getResponse(request);
			
			if(response.getStatus().equalsIgnoreCase("SUCCESS")) {
				String status = response.getValue(TASK_STATUS);
				String taskId = response.getValue(TASK_ID);
				logger.info(status + ", task ID:" + taskId);
			} else {
				String errorCode = response.getErrorCode();
				String errorMsg = response.getErrorMessage();
				logger.error("Error-code : " + errorCode + " " + errorMsg);
			}
		}

	}

}
