package com.itt.tds.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.core.Task;
import com.itt.tds.core.TaskResult;

public class Client {
	Properties prop = new Properties();
	InputStream input = null;

	protected Client() {
		try {
			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);
		} catch (Exception e) {
			System.err.println("error: unable to find config file. Run generate-config to generate one");
		}
	}

	String queueTask(File task, List<String> parameters) {

		TDSRequest request = new TDSRequest();
		request.setProtocolVersion("1.0");
		request.setProtocolFormat("JSON");
		request.setSourceIp("127.0.0.1");
		request.setSourcePort(10001);
		request.setDestIp(prop.getProperty("co-ordinator-ip"));
		request.setDestPort(Integer.parseInt(prop.getProperty("co-ordinator-port")));
		request.setMethod("client-queueTask");
		request.setParameters("taskName", task.getName());
		request.setParameters("parameters", parameters.toString());
		request.setParameters("hostName", getHostName());
		request.setParameters("userName", System.getProperty("user.name"));
		request.setData(convertToByte(task));

		TDSResponse response = Server.getResponse(request);
		String status = response.getValue("taskStatus");
		String taskId = response.getValue("taskId");
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