package com.itt.tds.comm;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.itt.tds.logging.TDSLogger;

public class TDSServer {
	static Logger logger = new TDSLogger().getLogger();

	public static ServerSocket getServerSocket(String ip, int port) {
		ServerSocket serverSocket = null;
		SocketAddress socketAdresss = new InetSocketAddress(ip, port);

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(socketAdresss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return serverSocket;
	}
}
