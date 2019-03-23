package com.itt.tds.comm;

import com.itt.tds.TDSExceptions.InvalidSerializedStringException;
import com.itt.tds.TDSExceptions.InvalidTDSProtocolObjectException;

/**
 * 
 */
public interface TDSSerializer {
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	// DeSerialize the String data into a TDSProtocol object
	public TDSProtocol DeSerialize(String data) throws InvalidSerializedStringException;

	// Serialize the TDSProtocol object to a String format
	public String Serialize(TDSProtocol protocol) throws InvalidTDSProtocolObjectException;

}