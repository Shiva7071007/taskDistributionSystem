package com.itt.tds.coordinator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.logging.TDSLogger;

import picocli.CommandLine.Command;

@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the server")
public class Server implements Runnable{
	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {

		logger.debug("reading server configuration...");
		TDSConfiguration tdsCFG = TDSConfiguration.getInstance();
		String serverIp = null;
		int serverPort = 0;
		try {
			serverIp = tdsCFG.getCoordinatorIP();
			serverPort = tdsCFG.getCoordinatorPort();
		} catch (Exception e1) {
			logger.error("failed to read configuration for starting the server", e1);
			logger.warn("retrying reading configuration");

			try {
				serverIp = tdsCFG.getCoordinatorIP();
				serverPort = tdsCFG.getCoordinatorPort();
			} catch (Exception e) {
				logger.error("failed to read configuration again for starting the server", e1);
				logger.fatal("closing the co-ordinator");
				System.exit(0);
			}
		}

		logger.info("Starting the Server on ip: " + serverIp + ":" + serverPort);
		ServerSocket serverSocket = null;
		SocketAddress socketAdresss = new InetSocketAddress(serverIp, serverPort);
		try {
			serverSocket = new ServerSocket();
		} catch (IOException e2) {
			logger.error("failed to start the sever. Retrying again...");
			try {
				serverSocket = new ServerSocket();
			} catch (IOException e) {
				logger.fatal("Unable to start server", e);
				logger.info("closing co-ordinator.");
				System.exit(0);
			}
		}

		try {
			serverSocket.bind(socketAdresss);
		} catch (IOException e2) {
			logger.error("failed to bind address " + serverIp + ":" + serverPort + " to the sever. Retrying again...");
			try {
				serverSocket.bind(socketAdresss);
			} catch (IOException e) {
				logger.fatal("failed to bind address", e);
				try {
					serverSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				logger.warn("Make sure adress is not in used. Check and start again.");
				logger.info("closing co-ordinator.");
				System.exit(0);
			}
		}
		logger.info("listening requests on : " + serverSocket.getLocalSocketAddress());

		// As per requirements, we want to have a multi-threaded server in which
		// each request will have its own dispatcher.

		// is this the right way???????
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
