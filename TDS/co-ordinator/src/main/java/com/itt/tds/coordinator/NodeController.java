package com.itt.tds.coordinator;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.NodeTasks.NodeAdd;
import com.itt.tds.coordinator.NodeTasks.SaveResult;
import com.itt.tds.logging.TDSLogger;

/**
 * 
 */
public class NodeController implements TDSController{
	private static final Object NODE_ADD = "node-add";
	private static final Object NODE_SAVE_RESULT = "node-saveResult";
	static Logger logger = new TDSLogger().getLogger();

    /**
     * Default constructor
     */
    public NodeController() {
    }

	@Override
	public TDSResponse processRequest(TDSRequest request) {
		logger.info("processing request");
		try {
			if (request.getMethod().equals(NODE_ADD))
				return NodeAdd.addNode(request);
			if (request.getMethod().equals(NODE_SAVE_RESULT))
				return SaveResult.addTaskResult(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}