package com.itt.tds.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.InvalidSerializedStringException;
import com.itt.tds.TDSExceptions.InvalidTDSProtocolObjectException;
import com.itt.tds.TDSExceptions.ServerCommunicationException;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class TDSClient {
	static Logger logger = new TDSLogger().getLogger();

	public static TDSResponse getResponse (TDSRequest request, int timeout) throws ServerCommunicationException, InvalidSerializedStringException, InvalidTDSProtocolObjectException {
		TDSResponse response = null;
		try (Socket socket = new Socket(request.getDestIp(), request.getDestPort())) {

			logger.debug("Socket opened : " + socket);

			socket.setSoTimeout(timeout * 60 * 1000);

			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer(request.getProtocolFormat());
			String requestData = dataSerializer.Serialize(request);
			logger.trace("Serialised request : " + requestData);

			BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

			serverWriter.println(requestData);
			String responseData = serverReader.readLine();
			logger.trace("serialised response got : " + responseData);

			response = (TDSResponse) dataSerializer.DeSerialize(responseData);
		} catch (SocketException e) {
			throw new ServerCommunicationException(TDSError.RESPONSE_TIMEOOUT, e);
		} catch (IOException io) {
			throw new ServerCommunicationException(TDSError.DESTINATION_SERVER_NOT_FOUND, io);
		}

		return response;
	}
}
