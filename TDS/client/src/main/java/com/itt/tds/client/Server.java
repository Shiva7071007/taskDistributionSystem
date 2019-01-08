package com.itt.tds.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.comm.TDSSerializer;
import com.itt.tds.comm.TDSSerializerFactory;

public class Server {
	static TDSResponse getResponse(TDSRequest request) {
		TDSResponse response = null;
		try (Socket socket = new Socket(request.getDestIp(), request.getDestPort())) {
			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");
			String requestData = dataSerializer.Serialize(request);

			BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

			serverWriter.println(requestData);
			String responseDAta = serverReader.readLine();

			response = (TDSResponse) dataSerializer.DeSerialize(responseDAta);

		} catch (Exception e) {
			System.err.println("error: Unable to communicate with server");
		}

		return response;
	}
}
