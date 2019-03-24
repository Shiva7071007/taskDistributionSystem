package com.itt.tds.node;

import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.TDSExceptions.RuntimeExceptions.CommunicationException;
import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.utility.Utility;
import com.itt.tds.errorCodes.TDSError;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 */
@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the node")
public class NodeRegisterer extends Node implements Runnable {

	private static final String NODE_ID = "nodeId";

	@Option(names = { "-t",
			"--timeout" }, description = "timeout for valid connection with co-ordinator in minutes. Valid values: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
	int timeout = 10;

	@Override
	public void run() {

		TDSRequest request = getNodeRegisterRequest();

		TDSResponse response = null;

		long timetoWait = System.currentTimeMillis() + (timeout * 60 * 1000);

		while (response == null) {
			long remainningTime = timetoWait - System.currentTimeMillis();
			if (remainningTime <= 0)
				break;

			try {
				response = TDSClient.getResponse(request, timeout);
			} catch (ServerCommunicationException e) {
				throw new CommunicationException("Something went wrong while communicating with server. Please refer logs for more details", e);
			}
		}

		if (response == null) {
			throw new CommunicationException(TDSError.DESTINATION_SERVER_NOT_FOUND);
		}

		if (response.getStatus().equals(SUCCESS)) {
			String nodeId = response.getValue(NODE_ID);
			logger.info("Succesfully registered Node with Node Id = " + nodeId);
			NodeServer.startServer();
		} else {
			Utility.displayErrorMsg(response);
		}
	}
}
