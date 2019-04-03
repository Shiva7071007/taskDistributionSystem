package com.itt.tds.node;

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

public class NodeRequestHandler implements Runnable {
	static Logger logger = new TDSLogger().getLogger();

	Socket sock;

	public NodeRequestHandler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		NodeConfiguration nodeCFG = NodeConfiguration.getInstance();
		try {
			logger.info(" processing request on " + sock);
			String requestData = Utility.getRequest(sock);

			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer(nodeCFG.getProtocolFormat());
			TDSRequest request = (TDSRequest) dataSerializer.DeSerialize(requestData);
			TDSResponse response = Controller.processRequest(request);
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
