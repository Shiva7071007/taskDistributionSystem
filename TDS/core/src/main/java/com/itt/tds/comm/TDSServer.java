package com.itt.tds.comm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.CoreException.CommException.FailedServerCreationException;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class TDSServer implements AutoCloseable {
	static Logger logger = new TDSLogger().getLogger();

	public static ServerSocket getServerSocket(String ip, int port) throws FailedServerCreationException {
		ServerSocket serverSocket = null;
		SocketAddress socketAdresss = new InetSocketAddress(ip, port);

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(socketAdresss);
		} catch (IOException e) {
			throw new FailedServerCreationException(TDSError.FAILED_SERVER_CREATION, e.getCause());
		}
		
		return serverSocket;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
