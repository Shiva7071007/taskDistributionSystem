package com.itt.tds.comm;

import static org.junit.Assert.*;

import org.junit.Test;


public class JSONSerializerTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testDeSerialize() {
		TDSRequest reqObject = new TDSRequest();
		reqObject.setProtocolVersion("1.0");
		reqObject.setProtocolFormat("JSON");
		reqObject.setSourceIp("127.0.0.1");
		reqObject.setSourcePort(10001);
		reqObject.setDestIp("127.0.0.1");
		reqObject.setDestPort(10002);
		reqObject.setMethod("node-add");
		reqObject.setParameters("node-ip", "127.0.0.1");
		reqObject.setParameters("node-name", "node1");

		String requestObjectString = "{\"protocolVersion\":\"1.0\",\"protocolFormat\":\"JSON\",\"protocolType\":\"request\",\"sourceIp\":\"127.0.0.1\",\"sourcePort\":10001,\"destIp\":\"127.0.0.1\",\"destPort\":10002,\"headers\":{\"node-ip\":\"127.0.0.1\",\"node-name\":\"node1\",\"method\":\"node-add\"},\"data\":null}";

		JSONSerializer jSer = new JSONSerializer();
		TDSRequest result = (TDSRequest) jSer.DeSerialize(requestObjectString);
		
		assertEquals(reqObject.getHeaders(), result.getHeaders());
		assertEquals(reqObject.getProtocolType(), result.getProtocolType());
		assertEquals(reqObject.getSourceIp(), result.getSourceIp() );
		
	}

	@Test
	public void testSerialize() {
		TDSRequest reqObject = new TDSRequest();
		reqObject.setProtocolVersion("1.0");
		reqObject.setProtocolFormat("JSON");
		reqObject.setSourceIp("127.0.0.1");
		reqObject.setSourcePort(10001);
		reqObject.setDestIp("127.0.0.1");
		reqObject.setDestPort(10002);
		reqObject.setMethod("node-add");
		reqObject.setParameters("node-ip", "127.0.0.1");
		reqObject.setParameters("node-name", "node1");

		String requestObjectString = "{\"protocolVersion\":\"1.0\",\"protocolFormat\":\"JSON\",\"protocolType\":\"request\",\"sourceIp\":\"127.0.0.1\",\"sourcePort\":10001,\"destIp\":\"127.0.0.1\",\"destPort\":10002,\"headers\":{\"node-ip\":\"127.0.0.1\",\"node-name\":\"node1\",\"method\":\"node-add\"},\"data\":null}";

		JSONSerializer jSer = new JSONSerializer();
		String result = jSer.Serialize(reqObject);

		assertEquals(result, requestObjectString);
	}

}
