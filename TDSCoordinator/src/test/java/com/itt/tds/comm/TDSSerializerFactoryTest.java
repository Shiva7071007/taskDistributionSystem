package com.itt.tds.comm;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TDSSerializerFactoryTest {

	private static TDSResponse resObject = null;
	private static TDSRequest reqObject = null;

	@BeforeClass
	public static void prepareResponseObject() {
		resObject = new TDSResponse();
		resObject.setProtocolVersion("1.0");
		resObject.setProtocolFormat("JSON");
		resObject.setSourceIp("127.0.0.1");
		resObject.setSourcePort(10001);
		resObject.setDestIp("127.0.0.1");
		resObject.setDestPort(10002);
		resObject.setStatus("success");
		resObject.setErrorCode("404");
		resObject.setErrorMessage("message");
		resObject.setValue("node-id", "121");
	}

	@BeforeClass
	public static void prepareRequestObject() {
		reqObject = new TDSRequest();
		reqObject.setProtocolVersion("1.0");
		reqObject.setProtocolFormat("JSON");
		reqObject.setSourceIp("127.0.0.1");
		reqObject.setSourcePort(10001);
		reqObject.setDestIp("127.0.0.1");
		reqObject.setDestPort(10002);
		reqObject.setMethod("node-add");
		reqObject.setParameters("node-ip", "127.0.0.1");
		reqObject.setParameters("node-name", "node1");
	}

	@Test
	public void testGetSerializerForJSonSerializerUsingRequest() throws Exception {
		String requestObjectJSONString = "{\"protocolVersion\":\"1.0\",\"protocolFormat\":\"JSON\",\"protocolType\":\"request\",\"sourceIp\":\"127.0.0.1\",\"sourcePort\":10001,\"destIp\":\"127.0.0.1\",\"destPort\":10002,\"headers\":{\"node-ip\":\"127.0.0.1\",\"node-name\":\"node1\",\"method\":\"node-add\"},\"data\":null}";

		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");

		String result = dataSerializer.Serialize(reqObject);

		assertEquals(result, requestObjectJSONString);
	}

	@Test
	public void testGetSerializerForJSonDeSerializerUsingRequest() throws Exception {
		String requestObjectJSONString = "{\"protocolVersion\":\"1.0\",\"protocolFormat\":\"JSON\",\"protocolType\":\"request\",\"sourceIp\":\"127.0.0.1\",\"sourcePort\":10001,\"destIp\":\"127.0.0.1\",\"destPort\":10002,\"headers\":{\"node-ip\":\"127.0.0.1\",\"node-name\":\"node1\",\"method\":\"node-add\"},\"data\":null}";

		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");

		TDSRequest result = (TDSRequest) dataSerializer.DeSerialize(requestObjectJSONString);

		assertEquals(reqObject.getHeaders(), result.getHeaders());
		assertEquals(reqObject.getProtocolType(), result.getProtocolType());
		assertEquals(reqObject.getSourceIp(), result.getSourceIp());
	}

	@Test
	public void testGetSerializerForJSonSerializerUsingResponse() throws Exception {
		String responseObjectString = "{\"protocolVersion\":\"1.0\",\"protocolFormat\":\"JSON\",\"protocolType\":\"response\",\"sourceIp\":\"127.0.0.1\",\"sourcePort\":10001,\"destIp\":\"127.0.0.1\",\"destPort\":10002,\"headers\":{\"error-message\":\"message\",\"status\":\"success\",\"node-id\":\"121\",\"error-code\":\"404\"},\"data\":null}";

		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");

		String result = dataSerializer.Serialize(resObject);

		assertEquals(result, responseObjectString);
	}

	@Test
	public void testGetSerializerForJSonDeSerializerUsingResponse() throws Exception {
		String responseObjectString = "{\"protocolVersion\":\"1.0\",\"protocolFormat\":\"JSON\",\"protocolType\":\"response\",\"sourceIp\":\"127.0.0.1\",\"sourcePort\":10001,\"destIp\":\"127.0.0.1\",\"destPort\":10002,\"headers\":{\"error-message\":\"message\",\"status\":\"success\",\"node-id\":\"121\",\"error-code\":\"404\"},\"data\":null}";
		
		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("json");

		TDSResponse result = (TDSResponse) dataSerializer.DeSerialize(responseObjectString);

		assertEquals(resObject.getHeaders(), result.getHeaders());
		assertEquals(resObject.getProtocolType(), result.getProtocolType());
		assertEquals(resObject.getSourceIp(), result.getSourceIp());
	}

	@Test
	public void testGetSerializerForXMLSerializerUsingRequest() throws Exception {
		String requestObjectXMLString = "<TDSRequest><protocolVersion>1.0</protocolVersion><protocolFormat>JSON</protocolFormat><protocolType>request</protocolType><sourceIp>127.0.0.1</sourceIp><sourcePort>10001</sourcePort><destIp>127.0.0.1</destIp><destPort>10002</destPort><headers><node-ip>127.0.0.1</node-ip><node-name>node1</node-name><method>node-add</method></headers><data/></TDSRequest>";
		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("xml");

		String result = dataSerializer.Serialize(reqObject);
		System.out.println(result);

		assertEquals(result, requestObjectXMLString);
	}
	
	@Test
	public void testGetSerializerForXMLDeSerializerUsingRequest() throws Exception {
		String requestObjectXMLString = "<TDSRequest><protocolVersion>1.0</protocolVersion><protocolFormat>JSON</protocolFormat><protocolType>request</protocolType><sourceIp>127.0.0.1</sourceIp><sourcePort>10001</sourcePort><destIp>127.0.0.1</destIp><destPort>10002</destPort><headers><node-ip>127.0.0.1</node-ip><node-name>node1</node-name><method>node-add</method></headers><data/></TDSRequest>";
		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("xml");

		TDSRequest result = (TDSRequest) dataSerializer.DeSerialize(requestObjectXMLString);

		assertEquals(reqObject.getHeaders(), result.getHeaders());
		assertEquals(reqObject.getProtocolType(), result.getProtocolType());
		assertEquals(reqObject.getSourceIp(), result.getSourceIp());
	}
	
	@Test
	public void testGetSerializerForXMLSerializerUsingResponse() throws Exception {
		String responseObjectXMLString = "<TDSResponse><protocolVersion>1.0</protocolVersion><protocolFormat>JSON</protocolFormat><protocolType>response</protocolType><sourceIp>127.0.0.1</sourceIp><sourcePort>10001</sourcePort><destIp>127.0.0.1</destIp><destPort>10002</destPort><headers><error-message>message</error-message><status>success</status><node-id>121</node-id><error-code>404</error-code></headers><data/></TDSResponse>";
		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("xml");

		String result = dataSerializer.Serialize(resObject);

		assertEquals(result, responseObjectXMLString);
	}

	@Test
	public void testGetSerializerForXMLDeSerializerUsingResponse() throws Exception {
		String responseObjectXMLString = "<TDSResponse><protocolVersion>1.0</protocolVersion><protocolFormat>JSON</protocolFormat><protocolType>response</protocolType><sourceIp>127.0.0.1</sourceIp><sourcePort>10001</sourcePort><destIp>127.0.0.1</destIp><destPort>10002</destPort><headers><error-message>message</error-message><status>success</status><node-id>121</node-id><error-code>404</error-code></headers><data/></TDSResponse>";
		TDSSerializer dataSerializer = TDSSerializerFactory.getSerializer("xml");

		TDSResponse result = (TDSResponse) dataSerializer.DeSerialize(responseObjectXMLString);

		assertEquals(resObject.getHeaders(), result.getHeaders());
		assertEquals(resObject.getProtocolType(), result.getProtocolType());
		assertEquals(resObject.getSourceIp(), result.getSourceIp());
	}
}
