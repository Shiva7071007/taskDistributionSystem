package com.itt.tds.node;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.itt.tds.logging.TDSLogger;

public class NodeServer {
	static Logger logger = new TDSLogger().getLogger();

	public static void startServer() {
		logger.debug("reading Node server configuration...");
		NodeConfiguration nodeCFG = NodeConfiguration.getInstance();
		String nodeServerIp = nodeCFG.getSourceIp();
		int nodeServerPort = nodeCFG.getSourcePort();

		logger.info("Starting the Server on ip: " + nodeServerIp + ":" + nodeServerPort);

		ServerSocket serverSocket = null;
		SocketAddress socketAdresss = new InetSocketAddress(nodeServerIp, nodeServerPort);

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(socketAdresss);
		} catch (IOException e2) {
			logger.error("failed to bind address " + nodeServerIp + ":" + nodeServerPort
					+ " to the sever. Retrying again...");
			try {
				serverSocket.bind(socketAdresss);
			} catch (IOException e) {
				logger.fatal("failed to bind address", e);
				logger.warn("Make sure adress is not in used. Check and start again.");
				System.exit(0);
			}
		}

		logger.info("listening requests on : " + serverSocket.getLocalSocketAddress());

		ExecutorService executor = Executors.newFixedThreadPool(2);

		while (true) {
			Socket sock = null;
			try {
				sock = serverSocket.accept();
				executor.execute(new NodeRequestHandler(sock));

			} catch (Exception e) {
				logger.fatal("something bad happend while initiating controller for socket : " + sock, e);
				try {
					logger.warn("closing socket " + sock + " due to failure");
					sock.close();
				} catch (IOException e1) {
					logger.warn("failed to close Socket : " + sock);
				}
			}
		}

	}
}
