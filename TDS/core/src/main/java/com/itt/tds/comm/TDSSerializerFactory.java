package com.itt.tds.comm;

public class TDSSerializerFactory {
	private static final String JSON = "json";
	private static final String XML = "xml";
	
	public static TDSSerializer getSerializer(String protocolFormat) throws Exception {
		if (protocolFormat.equalsIgnoreCase(JSON)) {
			return new JSONSerializer();
		} else if (protocolFormat.equalsIgnoreCase(XML)) {
			return new XMLSerializer();
		} else {
			throw new Exception("Invalid TDSSerializser Format : " + protocolFormat);
		}
	}
}
