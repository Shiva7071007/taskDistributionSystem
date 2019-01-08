package com.itt.tds.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate-config", mixinStandardHelpOptions = true, header = "create a properties file that will have necessary configuration for communication with server")
public class Config implements Runnable{
	@Option(names = "--ip", defaultValue = "127.0.0.1", description = "ip of the server. default: ${DEFAULT-VALUE}")
	String ip;
	
	@Option(names = "--port", defaultValue = "5000", description = "port of the server. default: ${DEFAULT-VALUE}")
	String port;
	
	private static String CO_ORDINATOR_IP = "co-ordinator-ip";
	private static String CO_ORDINATOR_PORT = "co-ordinator-port";
	private static String PROPERTIES_FILE = "config.properties";

	@Override
	public void run() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(PROPERTIES_FILE);

			// set the properties value
			prop.setProperty(CO_ORDINATOR_IP, ip);
			prop.setProperty(CO_ORDINATOR_PORT, port);

			// save properties to project root folder
			prop.store(output, "properties file for connecting to server");

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
	}
}
