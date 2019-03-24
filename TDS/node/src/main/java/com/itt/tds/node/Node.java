package com.itt.tds.node;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.core.NodeState;
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
	
	public TDSRequest getNodeRegisterRequest () {
		TDSRequest request = prepareNodeRequest();
		request.setMethod(NODE_ADD);
		request.setParameters(NODE_STATE, String.valueOf(NodeState.AVAILABLE));
		return request;
	}
	
}
