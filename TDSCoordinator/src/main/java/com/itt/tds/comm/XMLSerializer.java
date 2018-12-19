package com.itt.tds.comm;

import java.io.IOException;
import com.itt.tds.comm.CommConstants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLSerializer implements TDSSerializer {

	@Override
	public TDSProtocol DeSerialize(String data) {
		XmlMapper xmlMapper = new XmlMapper();
		TDSProtocol tdsProtocolObj = null;
		try {
			tdsProtocolObj = xmlMapper.readValue(data, TDSProtocol.class);

			String protocolType = tdsProtocolObj.getProtocolType();

			if (protocolType.equalsIgnoreCase(CommConstants.REQUEST)) {
				TDSRequest reqObject = null;
				reqObject = xmlMapper.readValue(data, TDSRequest.class);
				tdsProtocolObj = reqObject;
			} else if (protocolType.equalsIgnoreCase(CommConstants.RESPONSE)) {
				TDSResponse resObject = null;
				resObject = xmlMapper.readValue(data, TDSResponse.class);
				tdsProtocolObj = resObject;
			} else {
				System.err.println("invalid XML string for protocol object");
			}
		} catch (IOException e) {
			System.err.println("Incorrect format of XML");
			e.printStackTrace();
		}
		return tdsProtocolObj;
	}

	@Override
	public String Serialize(TDSProtocol protocol) {
		XmlMapper xmlMapper = new XmlMapper();
		String objString = "";
		try {
			objString = xmlMapper.writeValueAsString(protocol);
		} catch (JsonProcessingException e) {
			System.err.println("problem occured while serializing the protocol object");
			e.printStackTrace();
		}
		return objString;
	}

}
