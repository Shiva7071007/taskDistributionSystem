package com.itt.tds.comm;

import java.util.*;

/**
 * 
 */
public class TDSProtocol {

	/**
	 * Default constructor
	 */
	public TDSProtocol() {
	}

	/**
	 * 
	 */
	private String protocolVersion;

	/**
	 * 
	 */
	private String protocolFormat;

	/**
	 * 
	 */
	protected String protocolType;

	/**
	 * 
	 */
	private String sourceIp;

	/**
	 * 
	 */
	private int sourcePort;

	/**
	 * 
	 */
	private String destIp;

	/**
	 * 
	 */
	private int destPort;

	/**
	 * 
	 */
	private Hashtable<String, String> headers;

	/**
	 * 
	 */
	private byte[] data;

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getDestIp() {
		return destIp;
	}

	public void setDestIp(String destIp) {
		this.destIp = destIp;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public String getProtocolFormat() {
		return protocolFormat;
	}

	public void setProtocolFormat(String protocolFormat) {
		this.protocolFormat = protocolFormat;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public Hashtable<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Hashtable<String, String> headers) {
		this.headers = headers;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}