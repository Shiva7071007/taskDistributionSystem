package com.itt.tds.node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.comm.TDSSerializer;
import com.itt.tds.comm.TDSSerializerFactory;
import com.itt.tds.logging.TDSLogger;

public class NodeClient {

	static Logger logger = new TDSLogger().getLogger();

	static TDSResponse getResponse(TDSRequest request) {
		TDSResponse response = null;
		try (Socket socket = new Socket(request.getDestIp(), request.getDestPort())) {

			logger.trace("Socket opened : " + socket);

			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer(request.getProtocolFormat());
			String requestData = dataSerializer.Serialize(request);
			logger.debug("Serialised request : " + requestData);

			BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

			serverWriter.println(requestData);
			String responseData = serverReader.readLine();
			logger.debug("serialised response got : " + responseData);

			response = (TDSResponse) dataSerializer.DeSerialize(responseData);

		} catch (Exception e) {
			logger.error("Unable to communicate with server", e);
		}

		return response;

}
}
