package com.itt.tds.comm;

/**
 * 
 */
public interface TDSSerializer {
	final static String REQUEST = "request";
	final static String RESPONSE = "response";
	// DeSerialize the String data into a TDSProtocol object
	public TDSProtocol DeSerialize(String data);

	// Serialize the TDSProtocol object to a String format
	public String Serialize(TDSProtocol protocol);

}