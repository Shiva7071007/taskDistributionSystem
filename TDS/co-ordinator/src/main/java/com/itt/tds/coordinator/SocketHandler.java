package com.itt.tds.coordinator;

import java.net.Socket;
import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSResponse;
import com.itt.tds.comm.TDSSerializer;
import com.itt.tds.comm.TDSSerializerFactory;
import com.itt.tds.TDSExceptions.SocketReadWriteException;
import com.itt.tds.TDSExceptions.TDSProtocolSerializationException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

public class SocketHandler implements Runnable {

	static Logger logger = new TDSLogger().getLogger();
	Socket sock;

	public SocketHandler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		logger.info(" processing request for client : " + sock);

		try {
			String requestData = Utility.getRequest(sock);

			// Convert the request to TDSRequest
			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");
			TDSRequest request = (TDSRequest) dataSerializer.DeSerialize(requestData);

			TDSController controller = RequestDispatcher.getController(request);
			TDSResponse response = controller.processRequest(request);

			// Serialize the response data;
			String responseData = dataSerializer.Serialize(response);
			logger.trace("writing response for socket : " + sock + "==> \n " + responseData);
			Utility.writeResponse(sock, responseData);

		} catch (SocketReadWriteException | TDSProtocolSerializationException e) {
			logger.error("Exception occurred. Closing Socket : " + sock, e);
		} finally {
			Utility.closeSocket(sock);
		}
	}
}
