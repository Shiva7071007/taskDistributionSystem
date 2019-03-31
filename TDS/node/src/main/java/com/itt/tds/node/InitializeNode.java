package com.itt.tds.node;

import com.itt.tds.comm.TDSResponse;
import com.itt.tds.utility.Utility;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 */
@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the node")
public class InitializeNode extends Node implements Runnable {

	private static final String NODE_ID = "nodeId";

	@Option(names = { "-t",
			"--timeout" }, description = "timeout for valid connection with co-ordinator in minutes. Valid values: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
	int timeout = 10;

	@Override
	public void run() {

		TDSResponse response = registerNode(timeout);

		if (response.getStatus().equals(SUCCESS)) {
			String nodeId = response.getValue(NODE_ID);
			logger.info("Succesfully registered Node with Node Id = " + nodeId);
			NodeServer.startServer();
		} else {
			Utility.displayErrorMsg(response);
		}
	}
}
