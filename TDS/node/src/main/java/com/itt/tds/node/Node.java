package com.itt.tds.node;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.TDSExceptions.RuntimeExceptions.CommunicationException;
import com.itt.tds.comm.TDSClient;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.NodeState;
import com.itt.tds.core.TaskResult;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class Node {
	
	static Logger logger = new TDSLogger().getLogger();
	
	protected static final String SUCCESS = "SUCCESS";
	private static final String NODE_ADD = "node-add";
	private static final String NODE_STATE = "nodeState";

	private static final String NODE_POST_RESULT = "node-postResult";
	private static final String TASK_ERROR_MSG = "taskErrMsg";
	private static final String TASK_ERROR_CODE = "taskErrorCode";
	private static final String TASK_OUTCOME = "taskOutcome";
	private static final String TASK_ID = "taskId";
	
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

	public void postResult(TaskResult taskResult) {
		
		TDSRequest request = prepareNodeRequest();
		request.setMethod(NODE_POST_RESULT);
		request.setParameters(TASK_ID, String.valueOf(taskResult.getTaskId()));
		request.setParameters(TASK_OUTCOME, String.valueOf(taskResult.getTaskOutcome()));
		request.setParameters(TASK_ERROR_CODE, String.valueOf(taskResult.getErrorCode()));
		request.setParameters(TASK_ERROR_MSG, taskResult.getErrorMessage());
		request.setData(taskResult.getResultBuffer());
		
		TDSResponse response = null;
		int max_attempt = 10;
		int retry = 0;
		
		while (retry < max_attempt) {
			try {
				response = TDSClient.getResponse(request, 0);
				if (response.getStatus().equals(SUCCESS)) {
					logger.info("successfuly posted the result.");
					break;
				} else {
					Utility.displayErrorMsg(response);
					if(retry++ < max_attempt) {
						logger.error("Will retry in 5 second");
						Utility.sleep(30);
					}
				}
			} catch (ServerCommunicationException e) {
				if(retry++ < max_attempt) {
					logger.error("didnt get response. Will retry in 5 second", e);
					Utility.sleep(5);
					continue;
				}
				throw new CommunicationException("Something went wrong while communicating with server. Please refer logs for more details", e);
			}
		}
		
		logger.info("setting node state as ==> " + LocalNodeState.currentNodeState);
		LocalNodeState.currentNodeState = NodeState.AVAILABLE;
	}
	
}
