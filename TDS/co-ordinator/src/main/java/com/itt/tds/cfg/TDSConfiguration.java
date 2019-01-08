package com.itt.tds.cfg;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * TDSConfiguration allows access to all the configuration specified in TDS.xml
 * file. Example of the sample configuration file <tds> <database>
 * <db-connection-string></db-connection-string> </database> </tds>
 */
public class TDSConfiguration {
	private static volatile TDSConfiguration TDSConfigurationInstance;

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

	private NodeList getElementsByTagName(String tagName) throws Exception {
		NodeList tagNameList = null;
		//URI configFileName = getClass().getClassLoader().getResource("TDS.xml").toURI();//"src/main/resources/TDS.xml";
		String configFileName = "TDS.xml";
		File configFile = new File(configFileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document configXML = documentBuilder.parse(configFile);
		configXML.getDocumentElement().normalize();
		tagNameList = configXML.getElementsByTagName(tagName);
		return tagNameList;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getDBConnectionString() throws Exception {

		String dbConnectionString = null;
		String tagName = "database";

		NodeList databaseList = getElementsByTagName(tagName);
		Node nNode = databaseList.item(databaseList.getLength() - 1);

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			String dbConnectionUrl = element.getElementsByTagName("db-connection-string").item(0).getTextContent();
			String userName = element.getElementsByTagName("db-user-name").item(0).getTextContent();
			String userPassword = element.getElementsByTagName("db-user-password").item(0).getTextContent();

			dbConnectionString = dbConnectionUrl + "?user=" + userName + "&password=" + userPassword;
		}
		return dbConnectionString;
	}

	public int getMaxDBConnectionNumber() throws Exception {
		int maxDBConnection = 0;
		String tagName = "database";
		NodeList databaseList = getElementsByTagName(tagName);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			String maxDBConnectionConfig = element.getElementsByTagName("db-max-connection").item(0).getTextContent();
			maxDBConnection = Integer.parseInt(maxDBConnectionConfig);
		}
		return maxDBConnection;
	}

	public String getCoordinatorIP() throws Exception {
		String coordinatorIP = "";
		String tagName = "co-ordinator";
		NodeList databaseList = getElementsByTagName(tagName);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;

			coordinatorIP = element.getElementsByTagName("co-ordinator-ip").item(0).getTextContent();
		}
		return coordinatorIP;
	}
	
	public int getCoordinatorPort() throws Exception {
		int coordinatorPort = 0;
		String tagName = "co-ordinator";
		NodeList databaseList = getElementsByTagName(tagName);
		Node nNode = databaseList.item(databaseList.getLength() - 1);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) nNode;
			
			coordinatorPort = Integer.parseInt(element.getElementsByTagName("co-ordinator-port").item(0).getTextContent());
		}
		return coordinatorPort;
	}

}