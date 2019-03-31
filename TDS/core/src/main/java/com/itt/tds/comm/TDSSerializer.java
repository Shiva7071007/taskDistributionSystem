package com.itt.tds.comm;

import com.itt.tds.TDSExceptions.TDSProtocolSerializationException;

/**
 * 
 */
public interface TDSSerializer {
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	// DeSerialize the String data into a TDSProtocol object
	public TDSProtocol DeSerialize(String data) throws TDSProtocolSerializationException;

	// Serialize the TDSProtocol object to a String format
	public String Serialize(TDSProtocol protocol) throws TDSProtocolSerializationException;

}