package com.itt.tds.comm;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;

public class TDSResponseTest {

	@Test
	public void testGetStatus() {
		// Arrange
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		String key = "status";
		String value = "SUCCESS";
		headerParameters.put(key, value);
		TDSResponse resObject = new TDSResponse();
		resObject.setHeaders(headerParameters);

		// act
		String result = resObject.getStatus();

		// assert
		assertEquals(result, value);
	}

	@Test
	public void testSetStatus() {
		// arrange
		String key = "status";
		String value = "SUCCESS";
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		TDSResponse resObject1 = new TDSResponse();
		TDSResponse resObject2 = new TDSResponse();
		headerParameters.put(key, value);
		headerParameters.put("error-code", "");
		headerParameters.put("error-message", "");
		resObject1.setHeaders(headerParameters);

		// act
		resObject2.setStatus(value);
		System.out.println(resObject2.getHeaders());
		System.out.println(resObject1.getHeaders());

		// test
		assertEquals(resObject2.getHeaders(), resObject1.getHeaders());

	}

	@Test
	public void testGetErrorCode() {
		// Arrange
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		String key = "error-code";
		String value = "404";
		headerParameters.put(key, value);
		TDSResponse resObject = new TDSResponse();
		resObject.setHeaders(headerParameters);

		// act
		String result = resObject.getErrorCode();

		// assert
		assertEquals(result, value);
	}

	@Test
	public void testSetErrorCode() {
		// arrange
		String key = "error-code";
		String value = "404";
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		TDSResponse resObject1 = new TDSResponse();
		TDSResponse resObject2 = new TDSResponse();
		headerParameters.put(key, value);
		headerParameters.put("status", "");
		headerParameters.put("error-message", "");
		resObject1.setHeaders(headerParameters);

		// act
		resObject2.setErrorCode(value);

		// test
		assertEquals(resObject2.getHeaders(), resObject1.getHeaders());
	}

	@Test
	public void testGetErrorMessage() {
		// Arrange
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		String key = "error-message";
		String value = "Not Found";
		headerParameters.put(key, value);
		TDSResponse resObject = new TDSResponse();
		resObject.setHeaders(headerParameters);

		// act
		String result = resObject.getErrorMessage();

		// assert
		assertEquals(result, value);
	}

	@Test
	public void testSetErrorMessage() {
		// arrange
		String key = "error-message";
		String value = "Not Found";
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		TDSResponse resObject1 = new TDSResponse();
		TDSResponse resObject2 = new TDSResponse();
		headerParameters.put(key, value);
		headerParameters.put("status", "");
		headerParameters.put("error-code", "");
		resObject1.setHeaders(headerParameters);

		// act
		resObject2.setErrorMessage(value);

		// test
		assertEquals(resObject2.getHeaders(), resObject1.getHeaders());
	}

	@Test
	public void testGetValue() {
		// Arrange
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		String key = "custom";
		String value = "custom value";
		headerParameters.put(key, value);
		TDSResponse resObject = new TDSResponse();
		resObject.setHeaders(headerParameters);

		// act
		String result = resObject.getValue(key);

		// assert
		assertEquals(result, value);
	}

	@Test
	public void testSetValue() {
		// arrange
		String key = "custom";
		String value = "custom value";
		Hashtable<String, String> headerParameters = new Hashtable<String, String>();
		TDSResponse resObject1 = new TDSResponse();
		TDSResponse resObject2 = new TDSResponse();
		headerParameters.put(key, value);
		resObject1.setHeaders(headerParameters);

		// act
		resObject2.setValue(key, value);

		// assert
		assertEquals(resObject2.getHeaders().get(key), resObject1.getHeaders().get(key));
	}

	@Test
	public void testGetProtocolType() {
		// Arrange
		String protocolType = "response";
		TDSResponse resObject = new TDSResponse();

		// act
		String result = resObject.getProtocolType();

		// assert
		assertEquals(result, protocolType);
	}

}
