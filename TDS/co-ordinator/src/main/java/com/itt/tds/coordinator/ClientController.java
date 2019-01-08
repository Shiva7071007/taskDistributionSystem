package com.itt.tds.coordinator;

import java.util.*;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.Task;
import com.itt.tds.logging.TDSLogger;

/**
 * 
 */
public class ClientController implements TDSController {
	static Logger logger = new TDSLogger().getLogger();
	/**
	 * Default constructor
	 */
	public ClientController() {
	}


	private ClientRequest waitForClient() {
		// TODO implement here
		return null;
	}

	/**
	 * @param task
	 */
	private TDSResponse queueTask(TDSRequest request) {
		// do we really need marked members in TDSResponse
		TDSResponse resObject = new TDSResponse();
		resObject.setProtocolVersion("1.0");
		resObject.setProtocolFormat("JSON");
		resObject.setSourceIp("127.0.0.1"); //
		resObject.setSourcePort(10001);	//
		resObject.setDestIp("127.0.0.1");	//
		resObject.setDestPort(10002);	//
		resObject.setStatus("success");
		resObject.setErrorCode("0");
		resObject.setErrorMessage("no-error");
		resObject.setValue("taskId", "155");
		
		return resObject;
	}

	/**
	 * @param request
	 * @return 
	 */
	private TDSResponse queryResult(TDSRequest request) {
		return null;
	}

	@Override
	public TDSResponse processRequest(TDSRequest request) {
		logger.info("processing request");
		if (request.getMethod().equals("client-queueTask"))
			return queueTask(request);
		if (request.getMethod().equals("client-queryResult"))
			return queryResult(request);
		return null;
	}

}