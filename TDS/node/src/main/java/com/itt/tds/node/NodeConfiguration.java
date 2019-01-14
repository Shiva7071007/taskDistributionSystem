package com.itt.tds.node;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.itt.tds.logging.TDSLogger;

public class NodeConfiguration {
	private static volatile NodeConfiguration NodeConfigurationInstance;

	private static final String PROTOCOL_VERSION = "protocol-version";
	private static final String PROTOCOL_FORMAT = "protocol-format";
	private static final String CO_ORDINATOR_IP = "co-ordinator-ip";
	private static final String CO_ORDINATOR_PORT = "co-ordinator-port";
	private static final String PROPERTIES_FILE = "config.properties";
	private static final String LOG_LEVEL = "log-level";
	private static final String SOURCE_PORT = "source-port";
	private static final String SOURCE_IP = "source-ip";
	private static final String HOSTNAME = "hostName";
	private static final String USERNAME = "userName";

	static Logger logger = new TDSLogger().getLogger();

	static Properties prop = new Properties();

	private NodeConfiguration() {
		if (NodeConfigurationInstance != null) {
			throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
		}
	}

	public static NodeConfiguration getInstance() {
		if (NodeConfigurationInstance == null) { // Check for the first time

			synchronized (NodeConfiguration.class) { // Check for the second time.
				// if there is no instance available... create new one
				if (NodeConfigurationInstance == null)
					NodeConfigurationInstance = new NodeConfiguration();
			}
		}

		try {
			InputStream input = new FileInputStream(PROPERTIES_FILE);
			// load a properties file
			prop.load(input);

			StringWriter writer = new StringWriter();
			prop.list(new PrintWriter(writer));
			logger.trace(writer.getBuffer().toString());
		} catch (Exception e) {
			logger.error("Unable to find config file. Run generate-config to generate one");
		}
		return NodeConfigurationInstance;
	}

	public String getProtocolVersion() {
		return prop.getProperty(PROTOCOL_VERSION);
	}

	public String getProtocolFormat() {
		return prop.getProperty(PROTOCOL_FORMAT);
	}

	public String getDestinationIp() {
		return prop.getProperty(CO_ORDINATOR_IP);
	}

	public int getDestinationPort() {
		return Integer.parseInt(prop.getProperty(CO_ORDINATOR_PORT));
	}

	public String getLogLevel() {
		return prop.getProperty(LOG_LEVEL);
	}

	public String getHostName() {
		return prop.getProperty(HOSTNAME);
	}

	public String getSourceIp() {
		return prop.getProperty(SOURCE_IP);
	}

	public int getSourcePort() {
		return Integer.parseInt(prop.getProperty(SOURCE_PORT));
	}

	public String getUserName() {
		return prop.getProperty(USERNAME);
	}

}