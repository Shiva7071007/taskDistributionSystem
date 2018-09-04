package com.itt.tds.cfg;

import java.util.*;

/**
 * TDSConfiguration allows access to all the configuration specified in TDS.xml file. Example of the sample configuration file
 * <tds>
 *   <database>
 *    <db-connection-string></db-connection-string>
 *   </database>
 * </tds>
 */
public class TDSConfiguration {
	XMLConfiguration configRead = new XMLConfiguration("TDS.xml");
	
    /**
     * Default constructor
     */
    private TDSConfiguration() {
    }

    /**
     * Should return the instance of TDSConfiguration object, 
     * the code should make sure only one instance of the object in the application 
     * regardless of the number of times the getInstance() method is called on the object.
     * @return
     */
    public static synchronized TDSConfiguration getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getDBConnectionString() {
        // TODO implement here
    	String settingValue = configRead.getString("tds");
        //return "";
    	return settingValue;
    }
    
    public static void main(String args[]) {
    	System.out.println("hello");
    }

}