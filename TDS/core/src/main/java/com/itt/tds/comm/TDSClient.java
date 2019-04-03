package com.itt.tds.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.TDSExceptions.TDSProtocolSerializationException;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class TDSClient {
	static Logger logger = new TDSLogger().getLogger();

	public static TDSResponse getResponse (TDSRequest request, int timeout) throws ServerCommunicationException {
		TDSResponse response = null;
		try (Socket socket = new Socket(request.getDestIp(), request.getDestPort())) {

			logger.info("Socket opened : " + socket);

			socket.setSoTimeout(timeout * 60 * 1000);

			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer(request.getProtocolFormat());
			String requestData = dataSerializer.Serialize(request);
			logger.info("Serialised request : " + requestData.split("\"data\":")[0]);

			BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

			serverWriter.println(requestData);
			String responseData = serverReader.readLine();
			logger.info("serialised response got : " + responseData.split("\"data\":")[0]);

			response = (TDSResponse) dataSerializer.DeSerialize(responseData);
		} catch (IOException | TDSProtocolSerializationException e) {
			throw new ServerCommunicationException(TDSError.RESPONSE_TIMEOOUT, e);
		}

		return response;
	}
}
