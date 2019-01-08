package com.itt.tds.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;

public class Client {
	private static String PROTOCOL_VERSION = "1.0";
	private static String PROTOCOL_FORMAT = "JSON";
	private static String SOURCE_IP = "127.0.0.1";
	private static int SOURCE_PORT = 10001;
	private static String CO_ORDINATOR_IP = "co-ordinator-ip";
	private static String CO_ORDINATOR_PORT = "co-ordinator-port";
	private static String CLIENT_QUEUE_TASK = "client-queueTask";
	private static String USER_NAME = "user.name";
	private static String TASK_NAME = "taskName";
	private static String PARAMETERS = "parameters";
	private static String HOSTNAME = "hostName";
	private static String USERNAME = "userName";
	private static String TASK_STATUS = "taskStatus";
	private static String TASK_ID = "taskId";
	private static String PROPERTIES_FILE = "config.properties";
	
	Properties prop = new Properties();

	protected Client() {
		try {
			InputStream input = null;
			input = new FileInputStream(PROPERTIES_FILE);
			// load a properties file
			prop.load(input);
		} catch (Exception e) {
			System.err.println("error: unable to find config file. Run generate-config to generate one");
		}
	}

	String queueTask(File task, List<String> parameters) {

		TDSRequest request = new TDSRequest();
		request.setProtocolVersion(PROTOCOL_VERSION);
		request.setProtocolFormat(PROTOCOL_FORMAT);
		request.setSourceIp(SOURCE_IP);
		request.setSourcePort(SOURCE_PORT);
		request.setDestIp(prop.getProperty(CO_ORDINATOR_IP));
		request.setDestPort(Integer.parseInt(prop.getProperty(CO_ORDINATOR_PORT)));
		request.setMethod(CLIENT_QUEUE_TASK);
		request.setParameters(TASK_NAME, task.getName());
		request.setParameters(PARAMETERS, parameters.toString());
		request.setParameters(HOSTNAME, getHostName());
		request.setParameters(USERNAME, System.getProperty(USER_NAME));
		request.setData(convertToByte(task));

		TDSResponse response = Server.getResponse(request);
		String status = response.getValue(TASK_STATUS);
		String taskId = response.getValue(TASK_ID);
		return status + ", task ID:" + taskId;

	}

	private String getHostName() {
		InetAddress inetAddress = null;
		String hostName = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();
			
		} catch (UnknownHostException e) {
			System.err.println("error: unable to read host name.");
		}
		return hostName;
	}

	private byte[] convertToByte(File task) {
		byte[] bytesArray = new byte[(int) task.length()];

		FileInputStream fis;
		try {
			fis = new FileInputStream(task);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();
		} catch (Exception e) {
			System.err.println("error: unable to read file");
		}

		return bytesArray;
	}
}