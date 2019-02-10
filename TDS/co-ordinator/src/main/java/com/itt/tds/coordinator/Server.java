package com.itt.tds.coordinator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.comm.TDSServer;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

import picocli.CommandLine.Command;

@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the server")
public class Server implements Runnable{
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		Level logLevel = Level.INFO;
		try {
			logLevel = Level.toLevel(tdsCFG.getCoordinatorLogLevel());
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		LogManager.getRootLogger().setLevel(logLevel);

		logger.debug("reading server configuration...");
		String serverIp = null;
		int serverPort = 0;
		
			serverIp = tdsCFG.getCoordinatorIP();
			serverPort = tdsCFG.getCoordinatorPort();

		logger.info("Starting the Server on ip: " + serverIp + ":" + serverPort);
		ServerSocket serverSocket = TDSServer.getServerSocket(serverIp, serverPort);
	
		logger.info("listening requests on : " + serverSocket.getLocalSocketAddress());

		while (true) {
			Socket sock = null;
			try {
				// socket object to receive incoming client requests
				sock = serverSocket.accept();
				logger.info("A new client is connected : " + sock);

				// Assigning new thread for this client
				Thread socketThread = new Thread(new SocketHandler(sock));
				socketThread.start();
			} catch (Exception e) {
				logger.fatal("something bad happend while initiating controller dispatcher for socket : " + sock, e);
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
