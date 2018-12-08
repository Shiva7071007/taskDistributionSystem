package com.itt.tds.comm;

import java.util.*;

/**
 * 
 */
public class TDSRequest extends TDSProtocol {
	
	private Hashtable<String, String> headerParameters = new Hashtable<String, String>();

	/**
	 * Default constructor
	 */
	public TDSRequest() {
	}

	/*******
	 * Sample TDSRequest JSON string 
	 * 
	 * { 
	 * 		"TDSProtocolVersion":"1.0",
	 * 		"TDSProtocolFormat":"json", 
	 * 		"type":"request", 
	 * 		"sourceIP":"127.0.0.1",
	 * 		"sourcePort":"10001", 
	 * 		"destinationIp":"127.0.0.1", 
	 * 		"destinationPort":"10002",
	 * 		"header":{ 
	 * 			"method":"node-add", 
	 * 			"node-name":"abc", 
	 * 			"node-ip":"ip-address",
	 * 			"node-port":"port" 
	 * 		}, 
	 * 		"payload":"base64string" 
	 * }
	 * 
	 *******/
	
	public String getMethod() {
		return headerParameters.get("method");
	}

	public void setMethod(String method) {
		setParameters("method", method);
	}
	
	public String getParameters(String key) {
		Hashtable<String, String> headerParameters = getHeaders();
		return headerParameters.get(key);
	}

	public void setParameters(String parameter, String value) {
		headerParameters.put(parameter, value);
		setHeaders(headerParameters);
	}

}
