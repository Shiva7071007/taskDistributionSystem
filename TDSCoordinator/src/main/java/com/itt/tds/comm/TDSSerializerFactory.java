package com.itt.tds.comm;

public class TDSSerializerFactory {
	public static TDSSerializer getSerializer(String protocolFormat) {
		if (protocolFormat.equalsIgnoreCase("json")) {
			return new JSONSerializer();
		} else if (protocolFormat.equalsIgnoreCase("xml")) {
			return new XMLSerializer();
		} else {
			System.err.println("Incorrect protocol format passed");
			return null;
		}
	}
}
