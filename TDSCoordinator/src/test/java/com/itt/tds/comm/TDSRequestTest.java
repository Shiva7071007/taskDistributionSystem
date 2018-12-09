package com.itt.tds.comm;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;

public class TDSRequestTest {
	
	@Test
	public void testGetProtocolType() {
		// Arrange
		String protocolType = "request";
		TDSRequest reqObject = new TDSRequest();

		// act
		String result = reqObject.getProtocolType();

		// assert
		assertEquals(result, protocolType);

	}

	@Test
	public void testGetMethod() {
		// Arrange
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		String key = "method";
		String value = "node-add";
		headerParameters.put(key, value);
		TDSRequest reqObject = new TDSRequest();
		reqObject.setHeaders(headerParameters);

		// act
		String result = reqObject.getMethod();

		// assert
		assertEquals(result, value);

	}

	@Test
	public void testSetMethod() {
		// arrange
		String key = "method";
		String value = "node-add";
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		TDSRequest reqObject1 = new TDSRequest();
		TDSRequest reqObject2 = new TDSRequest();
		headerParameters.put(key, value);
		reqObject1.setHeaders(headerParameters);

		// act
		reqObject2.setMethod(value);

		// test
		assertEquals(reqObject2.getHeaders(), reqObject1.getHeaders());
	}

	@Test
	public void testGetParameters() {
		// Arrange
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		String key = "ip";
		String value = "127.0.0.1";
		headerParameters.put(key, value);
		TDSRequest reqObject = new TDSRequest();
		reqObject.setHeaders(headerParameters);

		// act
		String result = reqObject.getParameters(key);

		// assert
		assertEquals(result, value);
	}

	@Test
	public void testSetParameters() {
		// arrange
		String key = "ip";
		String value = "127.0.0.1";
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		TDSRequest reqObject1 = new TDSRequest();
		TDSRequest reqObject2 = new TDSRequest();
		headerParameters.put(key, value);
		reqObject1.setHeaders(headerParameters);

		// act
		reqObject2.setParameters(key, value);

		// assert
		assertEquals(reqObject2.getHeaders().get(key), reqObject1.getHeaders().get(key));
	}

}
