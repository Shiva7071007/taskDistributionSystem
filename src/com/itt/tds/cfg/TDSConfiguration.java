package com.itt.tds.cfg;

import java.io.File;
import java.net.URL;

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
	// XMLConfiguration configRead = new XMLConfiguration("TDS.xml");

	/**
	 * Default constructor
	 */
	public TDSConfiguration() {
	}

	/**
	 * Should return the instance of TDSConfiguration object, the code should make
	 * sure only one instance of the object in the application regardless of the
	 * number of times the getInstance() method is called on the object.
	 * 
	 * @return
	 */
	public static synchronized TDSConfiguration getInstance() {
		TDSConfiguration tdsConfiguration = new TDSConfiguration();
		return tdsConfiguration;
	}

	/**
	 * @return
	 */
	public String getDBConnectionString() {

		String dbConnectionString = null;
		try {
			URL configFilePath = getClass().getResource("TDS.xml");
			File configFile = new File(configFilePath.getPath());
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document configXML = documentBuilder.parse(configFile);
			configXML.getDocumentElement().normalize();

			NodeList databaseList = configXML.getElementsByTagName("database");

			Node nNode = databaseList.item(databaseList.getLength() - 1);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;

				String dbConnectionUrl = element.getElementsByTagName("db-connection-string").item(0).getTextContent();
				String userName = element.getElementsByTagName("db-user-name").item(0).getTextContent();
				String userPassword = element.getElementsByTagName("db-user-password").item(0).getTextContent();

				dbConnectionString = dbConnectionUrl + "?user=" + userName + "&password=" + userPassword;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConnectionString;
	}

}