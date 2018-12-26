package com.itt.tds.comm;

public class TDSSerializerFactory {
	private static String JSON = "json";
	private static String XML = "xml";
	
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
