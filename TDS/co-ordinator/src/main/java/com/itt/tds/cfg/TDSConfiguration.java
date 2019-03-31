package com.itt.tds.cfg;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itt.tds.TDSExceptions.RuntimeExceptions.FatalException;
import com.itt.tds.errorCodes.TDSError;
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
	private static final String USE_SSL_FAlSE = "&useSSL=false";

	/**
	 * Default constructor
	 */
	private TDSConfiguration() {
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

	private String getElementsByTagName(String parentTag, String elementTag) {
		String configFileName = CONFIG_FILE_NAME;
		File configFile = new File(configFileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		String elementValue = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document configXML = documentBuilder.parse(configFile);
			configXML.getDocumentElement().normalize();
			NodeList tagNameList = configXML.getElementsByTagName(parentTag);
			Node nNode = tagNameList.item(tagNameList.getLength() - 1);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				elementValue = element.getElementsByTagName(elementTag).item(0).getTextContent();
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new FatalException(TDSError.UNABLE_TO_FIND_CONFIG, e);
		}
		return elementValue;
	}

	public String getDBConnectionString() {

		String dbConnectionString = null;

		String dbConnectionUrl = getElementsByTagName(DATABASE, DB_CONNECTION_STRING);
		String userName = getElementsByTagName(DATABASE, DB_USER_NAME);
		String userPassword = getElementsByTagName(DATABASE, DB_USER_PASSWORD);
		dbConnectionString = dbConnectionUrl + "?user=" + userName + "&password=" + userPassword + USE_SSL_FAlSE;
		return dbConnectionString;
	}

	public int getMaxDBConnectionNumber() {
		int maxDBConnection = 0;
		String maxDBConnectionConfig = getElementsByTagName(DATABASE, DB_MAX_CONNECTION);
		maxDBConnection = Integer.parseInt(maxDBConnectionConfig);
		return maxDBConnection;
	}

	public String getCoordinatorIP() {
		String coordinatorIP = "";
		coordinatorIP = getElementsByTagName(CO_ORDINATOR, CO_ORDINATOR_IP);
		return coordinatorIP;
	}

	public int getCoordinatorPort() {
		int coordinatorPort = 0;
		coordinatorPort = Integer.parseInt(getElementsByTagName(CO_ORDINATOR, CO_ORDINATOR_PORT));
		return coordinatorPort;
	}

	public String getCoordinatorProtocolFormat() {
		String coordinatorProtocolFormat = null;
		coordinatorProtocolFormat = getElementsByTagName(CO_ORDINATOR, PROTOCOL_FORMAT);
		return coordinatorProtocolFormat;
	}

	public String getCoordinatorProtocolVersion() {
		String coordinatorProtocolVersion = null;
		coordinatorProtocolVersion = getElementsByTagName(CO_ORDINATOR, PROTOCOL_VERSION);
		return coordinatorProtocolVersion;
	}

	public String getCoordinatorLogLevel() {
		String coordinatorLogLevel = null;
		coordinatorLogLevel = getElementsByTagName(CO_ORDINATOR, LOG_LEVEL);
		return coordinatorLogLevel;
	}
}