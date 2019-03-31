package com.itt.tds.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import com.itt.tds.TDSExceptions.RuntimeExceptions.CommunicationException;
import com.itt.tds.comm.TDSServer;
import com.itt.tds.logging.TDSLogger;

public class NodeServer {
	static Logger logger = new TDSLogger().getLogger();

	public static void startServer() {
		NodeConfiguration nodeCFG = NodeConfiguration.getInstance();
		String nodeServerIp = nodeCFG.getSourceIp();
		int nodeServerPort = nodeCFG.getSourcePort();

		ServerSocket serverSocket = null;

		serverSocket = TDSServer.getServerSocket(nodeServerIp, nodeServerPort);

		logger.info("listening requests on : " + serverSocket.getLocalSocketAddress());

		ExecutorService executor = Executors.newFixedThreadPool(2);

		while (true) {
			try {
				Socket sock = serverSocket.accept();
				executor.execute(new NodeRequestHandler(sock));
			} catch (IOException e) {
				throw new CommunicationException("Server socket was closed abruptly", e);
			}
		}

	}
}
