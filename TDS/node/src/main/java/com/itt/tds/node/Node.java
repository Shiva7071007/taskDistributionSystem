package com.itt.tds.node;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.core.NodeState;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 */
@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the node")
public class Node implements Runnable {
	private static final String NODE_ADD = "node-add";
	private static final String NODE_STATE = "nodeState";
	private static final String NODE_ID = "nodeId";

	@Option(names = { "-t",
			"--timeout" }, description = "timeout for valid connection with co-ordinator in minutes. Valid values: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
	int timeout = 10;

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		NodeConfiguration nodeCfg = NodeConfiguration.getInstance();

		TDSRequest request = new TDSRequest();
		request.setProtocolVersion(nodeCfg.getProtocolVersion());
		request.setProtocolFormat(nodeCfg.getProtocolFormat());
		request.setSourceIp(nodeCfg.getSourceIp());
		request.setSourcePort(nodeCfg.getSourcePort());
		request.setDestIp(nodeCfg.getDestinationIp());
		request.setDestPort(nodeCfg.getDestinationPort());
		request.setMethod(NODE_ADD);
		request.setParameters(NODE_STATE, String.valueOf(NodeState.AVAILABLE));

		TDSResponse response = null;

		long timetoWait = System.currentTimeMillis() + (timeout * 60 * 1000);
		while (response == null) {
			long remainningTime = timetoWait - System.currentTimeMillis();
			if (remainningTime <= 0)
				break;

			response = TDSClient.sendRequest(request);
		}

		if (response == null) {
			logger.error("Timeout");
		} else {
			if (response.getStatus().equals("SUCCESS")) {
				String id = response.getValue(NODE_ID);
				NodeServer.startServer();
			}

		}

	}
}
