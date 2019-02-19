package com.itt.tds.coordinator.NodeTasks;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.coordinator.Node;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;

public class SaveResult {

	public static TDSResponse addTaskResult(TDSRequest request) {
		Node node = null;
		TDSNodeRepository nodeRepo = new TDSNodeRepository();
		TDSResponse response = new TDSResponse();
		return null;
	}

}
