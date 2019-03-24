package com.itt.tds.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSResponse;
import com.itt.tds.comm.TDSSerializer;
import com.itt.tds.comm.TDSSerializerFactory;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.logging.TDSLogger;

public class NodeRequestHandler implements Runnable {
	static Logger logger = new TDSLogger().getLogger();

	Socket sock;

	public NodeRequestHandler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		NodeConfiguration nodeCFG = NodeConfiguration.getInstance();
		String requestData = getRequest(sock);
		logger.info(" processing request on " + sock);

		TDSSerializer dataSerializer;
		try {
			dataSerializer = TDSSerializerFactory.getSerializer(nodeCFG.getProtocolFormat());
			TDSRequest request = (TDSRequest) dataSerializer.DeSerialize(requestData);
			
			TDSResponse response = Controller.processRequest(request);
			String responseData = dataSerializer.Serialize(response);
			logger.trace("writing response for socket : " + sock + "==> \n " + responseData);
			writeResponse(sock, responseData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getRequest(Socket sock) {
		BufferedReader socketReader = null;
		String request = "";
		try {
			socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			request = socketReader.readLine();
		} catch (IOException e) {
			logger.error("Reading from socket failed for client : " + sock, e);
		}
		return request;
	}

	private void writeResponse(Socket sock, String responseData) {
		PrintWriter socketWriter = null;
		try {
			socketWriter = new PrintWriter(sock.getOutputStream(), true);
			socketWriter.println(responseData);
		} catch (IOException e) {
			logger.error("failed to write response for socket : " + sock, e);
		}
	}

}
