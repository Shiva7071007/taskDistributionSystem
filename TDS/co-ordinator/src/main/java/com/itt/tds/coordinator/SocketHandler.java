package com.itt.tds.coordinator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.itt.tds.comm.TDSResponse;
import com.itt.tds.comm.TDSSerializer;
import com.itt.tds.comm.TDSSerializerFactory;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.logging.TDSLogger;

public class SocketHandler implements Runnable {

	static Logger logger = TDSLogger.getLogger();

	Socket sock;

	public SocketHandler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		logger.info(" processing request for client : " + sock);

		try {
			String requestData = getRequest(sock);

			// Convert the request to TDSRequest
			TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");
			TDSRequest request = (TDSRequest) dataSerializer.DeSerialize(requestData);

			TDSController controller = RequestDispatcher.getController(request);
			TDSResponse response = controller.processRequest(request);

			// Serialize the response data;
			String responseData = dataSerializer.Serialize(response);
			logger.trace("writing response for socket : " + sock + "==> \n " + responseData);
			writeResponse(sock, responseData);

		} catch (Exception e) {
			logger.error("something bad happened for socket : " + sock, e);
			logger.trace("Socket : " + sock + " will be closed");
			try {
				sock.close();
			} catch (IOException e1) {
				logger.error("failed to close Socket : " + sock, e1);
			}
		} finally {
			try {
				logger.trace("Socket : " + sock + " will be closed");
				sock.close();
			} catch (IOException e1) {
				logger.error("failed to close Socket : " + sock, e1);
			}
		}
	}

	private String getRequest(Socket sock) {
		BufferedReader socketReader = null;
		String request = "";

		try {
			socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			request = socketReader.readLine(); // lines().collect(Collectors.joining());
		} catch (IOException e) {
			logger.error("Reading from socket failed for client : " + sock, e);
			logger.warn("retrying reading again");

			try {
				socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				request = socketReader.lines().collect(Collectors.joining());
			} catch (IOException e1) {
				logger.fatal("failed to read again for sock : " + sock, e1);
			}

		} finally {
			/******
			 * /* Closing the returned OutputStream will close the associated socket. /*
			 * have to do something about it
			 ******/
			// try {
			// socketReader.close();
			// } catch (IOException e) {
			// logger.fatal("failed to close BufferedReader for sock : " + sock, e);
			// }
		}
		logger.trace("request got from sock : " + sock + " ==> \n " + request);
		return request;
	}

	private void writeResponse(Socket sock, String responseData) {
		PrintWriter socketWriter = null;
		try {
			socketWriter = new PrintWriter(sock.getOutputStream(), true);
			socketWriter.println(responseData);
		} catch (IOException e) {
			logger.error("failed to write response for socket : " + sock, e);
			logger.warn("retrying writing again");

			try {
				socketWriter = new PrintWriter(sock.getOutputStream(), true);
				socketWriter.println(responseData);
			} catch (IOException e1) {
				logger.fatal("failed to write response again for socket : " + sock, e);
			}
		} finally {
			/******
			 * /* when you close the PrintWriter, /* it'll close the associated OutputStream
			 * /* which will close the associated socket.
			 ******/
			// socketWriter.close();
		}
	}

}
