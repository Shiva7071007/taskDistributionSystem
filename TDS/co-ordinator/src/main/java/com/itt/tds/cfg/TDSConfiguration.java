package com.itt.tds.cfg;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.exceptions.CoordinatorConfigurationException;
import com.itt.tds.logging.TDSLogger;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * TDSConfiguration allows access to all the configuration specified in TDS.xml
 * file. Example of the sample configuration file <tds> <database>
 * <db-connection-string></db-connection-string> </database> </tds>
 */
public class TDSConfiguration {
	private static volatile TDSConfiguration TDSConfigurationInstance;
	static Logger logger = new TDSLogger().getLogger();

	private static final String CONFIG_FILE_NAME = "TDS.xml";
	private static final String PROTOCOL_FORMAT = "co-ordinator-protocol-format";
	private static final String PROTOCOL_VERSION = "co-ordinator-protocol-version";
	private static final String LOG_LEVEL = "co-ordinator-log-level";
	private static final String DATABASE = "database";
	private static final String CO_ORDINATOR = "co-ordinator";
	private static final String CO_ORDINATOR_IP = "co-ordinator-ip";
	private static final String CO_ORDINATOR_PORT = "co-ordinator-port";
	private static final String DB_CONNECTION_STRING = "db-connection-string";
	private static final String DB_USER_NAME = "db-user-name";
	private static final String DB_USER_PASSWORD = "db-user-password";
	private static final String DB_MAX_CONNECTION = "db-max-connection";

	/**
	 * Default constructor
	 */
	private TDSConfiguration() {
		if (TDSConfigurationInstance != null) {
			throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
		}
	}

	/**
	 * Should return the instance of TDSConfiguration object, the code should make
	 * sure only one instance of the object in the application regardless of the
	 * number of times the getInstance() method is called on the object.
	 * 
	 * @return
	 */
	// Without volatile modifier, it is possible for another thread in Java to see
	// half initialized state of TDSConfigurationInstance variable
	public static TDSConfiguration getInstance() {
		// Double check locking pattern
		if (TDSConfigurationInstance == null) { // Check for the first time

			synchronized (TDSConfiguration.class) { // Check for the second time.
				// if there is no instance available... create new one
				if (TDSConfigurationInstance == null)
					TDSConfigurationInstance = new TDSConfiguration();
			}
		}
		return TDSConfigurationInstance;
	}

	private NodeList getElementsByTagName(String tagName) {
		NodeList tagNameList = null;
		String configFileName = CONFIG_FILE_NAME;
		File configFile = new File(configFileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document configXML = documentBuilder.parse(configFile);
			configXML.getDocumentElement().normalize();
			tagNameList = configXML.getElementsByTagName(tagName);
		} catch (Exception e) {
			throw new CoordinatorConfigurationException(TDSError.UNABLE_TO_FIND_CONFIG, e);
		}
		return tagNameList;
	}

	public String getDBConnectionString() {

		String dbConnectionString = null;

		NodeList databaseList = getElementsByTagName(DATABASE);
		Node nNode = databaseList.item(databaseList.getLength() - 1);

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			String dbConnectionUrl = element.getElementsByTagName(DB_CONNECTION_STRING).item(0).getTextContent();
			String userName = element.getElementsByTagName(DB_USER_NAME).item(0).getTextContent();
			String userPassword = element.getElementsByTagName(DB_USER_PASSWORD).item(0).getTextContent();

			dbConnectionString = dbConnectionUrl + "?user=" + userName + "&password=" + userPassword + "&useSSL=false";
		}
		return dbConnectionString;
	}

	public int getMaxDBConnectionNumber() {
		int maxDBConnection = 0;
		NodeList databaseList = getElementsByTagName(DATABASE);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			String maxDBConnectionConfig = element.getElementsByTagName(DB_MAX_CONNECTION).item(0).getTextContent();
			maxDBConnection = Integer.parseInt(maxDBConnectionConfig);
		}
		return maxDBConnection;
	}

	public String getCoordinatorIP() {
		String coordinatorIP = "";
		String tagName = "co-ordinator";
		NodeList databaseList = getElementsByTagName(tagName);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			coordinatorIP = element.getElementsByTagName(CO_ORDINATOR_IP).item(0).getTextContent();
		}
		return coordinatorIP;
	}

	public int getCoordinatorPort() {
		int coordinatorPort = 0;
		NodeList databaseList = getElementsByTagName(CO_ORDINATOR);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			coordinatorPort = Integer
					.parseInt(element.getElementsByTagName(CO_ORDINATOR_PORT).item(0).getTextContent());
		}
		return coordinatorPort;
	}

	public String getCoordinatorProtocolFormat() {
		String coordinatorProtocolFormat = null;
		NodeList databaseList = getElementsByTagName(CO_ORDINATOR);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			coordinatorProtocolFormat = element.getElementsByTagName(PROTOCOL_FORMAT).item(0).getTextContent();
		}
		return coordinatorProtocolFormat;
	}

	public String getCoordinatorProtocolVersion() {
		String coordinatorProtocolVersion = null;
		NodeList databaseList = getElementsByTagName(CO_ORDINATOR);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			coordinatorProtocolVersion = element.getElementsByTagName(PROTOCOL_VERSION).item(0).getTextContent();
		}
		return coordinatorProtocolVersion;
	}

	public String getCoordinatorLogLevel() {
		String coordinatorLogLevel = null;
		NodeList databaseList = getElementsByTagName(CO_ORDINATOR);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			coordinatorLogLevel = element.getElementsByTagName(LOG_LEVEL).item(0).getTextContent();
		}
		return coordinatorLogLevel;
	}

}