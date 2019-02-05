package com.itt.tds.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class DestinationComManager {
	static Logger logger = new TDSLogger().getLogger();

	public static TDSResponse getResponse(TDSRequest request) {
		TDSResponse response = null;
		try (Socket socket = new Socket(request.getDestIp(), request.getDestPort())) {

			logger.debug("Socket opened : " + socket);

			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer(request.getProtocolFormat());
			String requestData = dataSerializer.Serialize(request);
			logger.trace("Serialised request : " + requestData);

			BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

			serverWriter.println(requestData);
			String responseData = serverReader.readLine();
			logger.trace("serialised response got : " + responseData);

			response = (TDSResponse) dataSerializer.DeSerialize(responseData);

		} catch (Exception e) {
			logger.error(TDSError.DESTINATION_SERVER_NOT_FOUND.toString());
			logger.trace(e);
		}

		return response;
	}
}
