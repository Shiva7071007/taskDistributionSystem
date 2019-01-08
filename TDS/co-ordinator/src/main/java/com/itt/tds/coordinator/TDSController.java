package com.itt.tds.coordinator;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;

interface TDSController {
	public TDSResponse processRequest(TDSRequest request);
}
