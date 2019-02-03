package com.itt.tds.node.RequestHandler;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.Task;
import com.itt.tds.node.NodeConfiguration;

public class TaskExecuter {

	public static TDSResponse executeTask(TDSRequest request) {
		NodeConfiguration nodeCFG = NodeConfiguration.getInstance();
		TDSResponse response = new TDSResponse();
		response.setProtocolVersion(request.getProtocolVersion());
		response.setProtocolFormat(request.getProtocolFormat());
		response.setSourceIp(request.getDestIp());
		response.setSourcePort(request.getDestPort());
		response.setDestIp(request.getSourceIp());
		response.setDestPort(request.getSourcePort());
		
		Task task = new Task();
//		task.setTaskName(request.getParameters(TASK_NAME));
//		task.setTaskParameters(taskParameters);
		return null;
	}

}
