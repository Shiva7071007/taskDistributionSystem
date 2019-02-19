package com.itt.tds.comm;

/**
 * 
 */
public interface TDSSerializer {
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	// DeSerialize the String data into a TDSProtocol object
	public TDSProtocol DeSerialize(String data);

	// Serialize the TDSProtocol object to a String format
	public String Serialize(TDSProtocol protocol);

}