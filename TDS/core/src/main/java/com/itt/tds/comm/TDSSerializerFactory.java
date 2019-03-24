package com.itt.tds.comm;

import com.itt.tds.TDSExceptions.RuntimeExceptions.FatalException;

public class TDSSerializerFactory {
	private static final String JSON = "json";
	private static final String XML = "xml";
	
	public static TDSSerializer getSerializer(String protocolFormat) {
		if (protocolFormat.equalsIgnoreCase(JSON)) {
			return new JSONSerializer();
		} else if (protocolFormat.equalsIgnoreCase(XML)) {
			return new XMLSerializer();
		} else {
			throw new FatalException("Invalid TDSSerializser Format : " + protocolFormat);
		}
	}
}
