package com.itt.tds.node;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.RuntimeExceptions.FatalException;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.utility.Utility;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate-config", mixinStandardHelpOptions = true, header = "create a properties file that will have necessary configuration for communication with server")
public class ConfigGenerator implements Runnable {
	@Option(names = { "-i",
			"--ip" }, defaultValue = "127.0.0.1", description = "ip of the server. default: ${DEFAULT-VALUE}")
	String ip;

	@Option(names = { "-p",
			"--port" }, defaultValue = "5000", description = "port of the server. default: ${DEFAULT-VALUE}")
	String port;

	enum TDSProtocolFormat {
		JSON, XML
	};

	@Option(names = { "-f",
			"--protocol-format" }, description = "protocol format used for communication. Valid values: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
	TDSProtocolFormat protocolFormat = TDSProtocolFormat.JSON;

	@Option(names = { "-v",
			"--protocol-version" }, description = "protocol version used for communication. (default: ${DEFAULT-VALUE})")
	String protocolVersion = "1.0";

	enum LogLevel {
		ALL, TRACE, DEBUG, WARN, ERROR, INFO
	};

	@Option(names = { "-l",
			"--log-level" }, description = "log level for showing logs. Valid values: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
	LogLevel logLevel = LogLevel.INFO;

	@Option(names = "--source-ip", description = "IP on which node will run. (default: ${DEFAULT-VALUE})")
	String sourceIP = Utility.getSourceIp();

	@Option(names = "--source-port", description = "Port on which node will listen request. (default: ${DEFAULT-VALUE})")
	Integer sourcePort = 5001;

	@Option(names = "--hostname", description = "HostName of current machine. (default: ${DEFAULT-VALUE})")
	String hostname = Utility.getHostName();

	@Option(names = "--user-name", description = "user name of current machine. (default: ${DEFAULT-VALUE})")
	String userName = Utility.getUserName();

	private static final String PROPERTIES_FILE = "config.properties";
	private static final String CO_ORDINATOR_IP = "co-ordinator-ip";
	private static final String CO_ORDINATOR_PORT = "co-ordinator-port";
	private static final String PROTOCOL_FORMAT = "protocol-format";
	private static final String PROTOCOL_VERSION = "protocol-version";
	private static final String LOG_LEVEL = "log-level";
	private static final String SOURCE_PORT = "source-port";
	private static final String SOURCE_IP = "source-ip";
	private static final String HOSTNAME = "hostName";
	private static final String USERNAME = "userName";

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		Level level = Level.toLevel(logLevel.toString());
		LogManager.getRootLogger().setLevel(level);

		try (OutputStream output = new FileOutputStream(PROPERTIES_FILE);) {
			Properties prop = new Properties();

			// set the properties value
			prop.setProperty(CO_ORDINATOR_IP, ip);
			prop.setProperty(CO_ORDINATOR_PORT, port);
			prop.setProperty(PROTOCOL_FORMAT, protocolFormat.toString());
			prop.setProperty(PROTOCOL_VERSION, protocolVersion);
			prop.setProperty(LOG_LEVEL, logLevel.toString());
			prop.setProperty(SOURCE_PORT, sourcePort.toString());
			prop.setProperty(SOURCE_IP, sourceIP);
			prop.setProperty(HOSTNAME, hostname);
			prop.setProperty(USERNAME, userName);

			// save properties to project root folder
			StringWriter writer = new StringWriter();
			prop.list(new PrintWriter(writer));
			logger.info(writer.getBuffer().toString());
			prop.store(output, "properties file for connecting to TDS co-ordinator server");

		} catch (IOException io) {
			throw new FatalException(TDSError.FAILED_TO_CREATE_CONFIG, io);
		}
	}
}
