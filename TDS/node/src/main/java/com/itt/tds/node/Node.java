package com.itt.tds.node;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.TDSExceptions.RuntimeExceptions.CommunicationException;
import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.NodeState;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class Node {
	
	static Logger logger = new TDSLogger().getLogger();
	
	protected static final String SUCCESS = "SUCCESS";
	private static final String NODE_ADD = "node-add";
	private static final String NODE_STATE = "nodeState";
	
	public TDSRequest prepareNodeRequest() {
		NodeConfiguration nodeCfg = NodeConfiguration.getInstance();
		
		TDSRequest request = new TDSRequest();
		request.setProtocolVersion(nodeCfg.getProtocolVersion());
		request.setProtocolFormat(nodeCfg.getProtocolFormat());
		request.setSourceIp(nodeCfg.getSourceIp());
		request.setSourcePort(nodeCfg.getSourcePort());
		request.setDestIp(nodeCfg.getDestinationIp());
		request.setDestPort(nodeCfg.getDestinationPort());
		
		return request;
	}
	
	protected TDSResponse registerNode (int timeout) {
		TDSRequest request = prepareNodeRequest();
		request.setMethod(NODE_ADD);
		request.setParameters(NODE_STATE, String.valueOf(NodeState.AVAILABLE));
		
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
		
		return response;
	}
	
}
