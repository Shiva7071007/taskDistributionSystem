package com.itt.tds.comm;

import java.util.*;

/**
 * 
 */
public interface TDSSerializer {
	//DeSerialize the String data into a TDSProtocol object
	public TDSProtocol DeSerialize(String data);

	//Serialize the TDSProtocol object to a String format
	public String Serialize(TDSProtocol protocol); 

}