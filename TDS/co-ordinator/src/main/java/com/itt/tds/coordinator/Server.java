package com.itt.tds.coordinator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.RuntimeExceptions.CommunicationException;
import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.comm.TDSServer;
import com.itt.tds.logging.TDSLogger;
import picocli.CommandLine.Command;

@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the server")
public class Server implements Runnable {
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		logger.debug("load server configuration...");
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();

		Level logLevel = Level.toLevel(tdsCFG.getCoordinatorLogLevel());
		LogManager.getRootLogger().setLevel(logLevel);

		String serverIp = tdsCFG.getCoordinatorIP();
		int serverPort = tdsCFG.getCoordinatorPort();
		logger.info("Starting the Server on ip: " + serverIp + ":" + serverPort);

		ServerSocket serverSocket = TDSServer.getServerSocket(serverIp, serverPort);
		logger.info("listening requests on : " + serverSocket.getLocalSocketAddress());

		ExecutorService executor = Executors.newFixedThreadPool(25);

		while (true) {
			try {
				
				Socket sock = serverSocket.accept();
				logger.info("A new client is connected : " + sock);
				executor.execute(new SocketHandler(sock));
				
			} catch (IOException e) {
				throw new CommunicationException("Server socket was closed abruptly", e);
			}
		}
	}
}
