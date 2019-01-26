package com.itt.tds.cfg;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itt.tds.logging.TDSLogger;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate-config", mixinStandardHelpOptions = true, header = "create a xml file containning configuration for starting Co-ordinator server")
public class ConfigGenerator implements Runnable {
	@Option(names = { "-i", "--ip" }, description = "ip of the server. default: ${DEFAULT-VALUE}")
	String ip = getSourceIp();

	@Option(names = { "-p",
			"--port" }, defaultValue = "5000", description = "port of the server. default: ${DEFAULT-VALUE}")
	Integer port = 5000;

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

	@Option(names = { "-c",
			"--db-connection-string" }, description = "Connection string for connecting to dataBase. (default: ${DEFAULT-VALUE})")
	String dbConnectionString = DB_CONNECTION_URL;

	@Option(names = { "-u",
			"--db-user-name" }, description = "User name for connecting to dataBase. (default: ${DEFAULT-VALUE})")
	String dbUserName = "root";

	@Option(names = { "-P", "--db-user-password" }, description = "User password for connecting to dataBase.")
	String dbUserPassword = "password";

	@Option(names = "--db-max-connection", description = "maximum number of connection to dataBase. (default: ${DEFAULT-VALUE})")
	Integer dbMaxConnection = 10;

	private static final String XML_FILE_PATH = "TDS.xml";
	private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/tds";
	private static final String DB_CONNECTION_STRING = "db-connection-string";
	private static final String DB_USER_NAME = "db-user-name";
	private static final String DB_USER_PASSWORD = "db-user-password";
	private static final String DB_MAX_CONNECTION = "db-max-connection";
	private static final String TDS = "tds";
	private static final String DATABASE = "database";
	private static final String CO_ORDINATOR = "co-ordinator";
	private static final String CO_ORDINATOR_IP = "co-ordinator-ip";
	private static final String CO_ORDINATOR_PORT = "co-ordinator-port";
	private static final String PROTOCOL_FORMAT = "co-ordinator-protocol-format";
	private static final String PROTOCOL_VERSION = "co-ordinator-protocol-version";
	private static final String LOG_LEVEL = "co-ordinator-log-level";

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		Level level = Level.toLevel(logLevel.toString());
		LogManager.getRootLogger().setLevel(level);

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement(TDS);
			document.appendChild(root);

			// database element
			Element database = document.createElement(DATABASE);
			root.appendChild(database);

			// coOrdinator element
			Element coOrdinator = document.createElement(CO_ORDINATOR);
			root.appendChild(coOrdinator);

			Element connectionString = document.createElement(DB_CONNECTION_STRING);
			connectionString.appendChild(document.createTextNode(dbConnectionString));
			database.appendChild(connectionString);

			Element userName = document.createElement(DB_USER_NAME);
			userName.appendChild(document.createTextNode(dbUserName));
			database.appendChild(userName);

			Element userPassword = document.createElement(DB_USER_PASSWORD);
			userPassword.appendChild(document.createTextNode(dbUserPassword));
			database.appendChild(userPassword);

			Element maxConnection = document.createElement(DB_MAX_CONNECTION);
			maxConnection.appendChild(document.createTextNode(dbMaxConnection.toString()));
			database.appendChild(maxConnection);

			Element coOrdinatorIP = document.createElement(CO_ORDINATOR_IP);
			coOrdinatorIP.appendChild(document.createTextNode(ip));
			coOrdinator.appendChild(coOrdinatorIP);

			Element coOrdinatorPort = document.createElement(CO_ORDINATOR_PORT);
			coOrdinatorPort.appendChild(document.createTextNode(port.toString()));
			coOrdinator.appendChild(coOrdinatorPort);
			
			Element coOrdinatorProtocolFormat = document.createElement(PROTOCOL_FORMAT);
			coOrdinatorProtocolFormat.appendChild(document.createTextNode(protocolFormat.toString()));
			coOrdinator.appendChild(coOrdinatorProtocolFormat);
			
			Element coOrdinatorProtocolversion = document.createElement(PROTOCOL_VERSION);
			coOrdinatorProtocolversion.appendChild(document.createTextNode(protocolVersion));
			coOrdinator.appendChild(coOrdinatorProtocolversion);
			
			Element coOrdinatorLogLevel = document.createElement(LOG_LEVEL);
			coOrdinatorLogLevel.appendChild(document.createTextNode(logLevel.toString()));
			coOrdinator.appendChild(coOrdinatorLogLevel);

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(XML_FILE_PATH));

			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			logger.error("Failed to create configuration file", e);
			;
		}
	}

	private String getSourceIp() {
		InetAddress inetAddress = null;
		String ip = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			ip = inetAddress.getHostAddress();

		} catch (UnknownHostException e) {
			logger.error("unable to read host ip.");
		}
		return ip;
	}

}
