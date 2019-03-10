package com.itt.tds.coordinator.NodeTasks;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.Node;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;
import com.itt.tds.utility.Utility;

public class SaveResult {

	public static TDSResponse addTaskResult(TDSRequest request) {
		Node node = null;
		TDSNodeRepository nodeRepo = new TDSNodeRepository();
		TDSResponse response = Utility.prepareResponse(request);
		return null;
	}

}
