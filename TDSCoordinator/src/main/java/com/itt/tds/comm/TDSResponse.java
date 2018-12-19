package com.itt.tds.comm;

import java.util.Hashtable;
import com.itt.tds.comm.CommConstants;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 */
public class TDSResponse extends TDSProtocol {

	private Hashtable<String, String> headerParameters = new Hashtable<String, String>();
	
	/**
	 * Default constructor
	 */
	public TDSResponse() {
		this.protocolType = CommConstants.RESPONSE;
		headerParameters.put(CommConstants.STATUS, "");
		headerParameters.put(CommConstants.ERROR_CODE, "");
		headerParameters.put(CommConstants.ERROR_MESSAGE, "");
		this.setHeaders(headerParameters);
	}

	/*******
	 * Sample TDSResponse JSON string 
	 * 
	 * {
	 * 		"TDSProtocolVersion":"1.0",
	 *      "TDSProtocolFormat":"JSON",
	 *      "type":"response",
	 *      "sourceIP":"127.0.0.1",
	 *      "sourcePort":"10001",
	 *      "destinationIp":"127.0.0.1",
	 *      "destinationPort":"1001",
	 *      "header":{  
	 *      	"status":"SUCESS",
	 *          "node-id":"121",
	 *          "error-code":"200",
	 *          "error-message":"message"
	 * 		},
	 *      "payload":"base64string"
	 * }
	 * 
	 *******/

	@JsonIgnore
	public String getStatus() {
		return getValue(CommConstants.STATUS);
	}
	
	public void setStatus(String status) {
		setValue(CommConstants.STATUS, status);
	}
	
	@JsonIgnore
	public String getErrorCode() {
		return getValue(CommConstants.ERROR_CODE);
	}

	public void setErrorCode(String errorCode) {
		setValue(CommConstants.ERROR_CODE, errorCode);
	}
	
	@JsonIgnore
	public String getErrorMessage() {
		return getValue(CommConstants.ERROR_MESSAGE);
	}
	
	public void setErrorMessage(String message) {
		setValue(CommConstants.ERROR_MESSAGE, message);
	}

	public String getValue(String key) {
		Hashtable<String, String> headers = getHeaders();
		return headers.get(key);
	}

	public void setValue(String key, String value) {
		headerParameters.put(key, value);
		setHeaders(headerParameters);
	}

}