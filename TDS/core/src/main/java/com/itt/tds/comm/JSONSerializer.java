package com.itt.tds.comm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.tds.comm.TDSProtocol;

public class JSONSerializer implements TDSSerializer {

	TDSProtocol tdsProtocolObj = null;

	@Override
	public TDSProtocol DeSerialize(String data)  {
		TDSProtocol tdsProtocolObj = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			tdsProtocolObj = mapper.readValue(data, TDSProtocol.class);
			String protocolType = tdsProtocolObj.getProtocolType();

			if (protocolType.equalsIgnoreCase(REQUEST)) {
				TDSProtocol reqObject = null;
				reqObject = mapper.readValue(data, TDSRequest.class);
				tdsProtocolObj = reqObject;
			} else if (protocolType.equalsIgnoreCase(RESPONSE)) {
				TDSResponse resObject = null;
				resObject = mapper.readValue(data, TDSResponse.class);
				tdsProtocolObj = resObject;
			} else {
				throw new Exception("Invalid TDSPProtocol Serialized json String");
			}
		} catch (Exception e) {
			System.err.println("Invalid TDSPProtocol Serialized json String");
			e.printStackTrace();
		}

		return tdsProtocolObj;
	}

	@Override
	public String Serialize(TDSProtocol protocol) {
		String objectString = "";
		ObjectMapper mapper = new ObjectMapper();

		try {
			objectString = mapper.writeValueAsString(protocol);
		} catch (JsonProcessingException e) {
			System.err.println("problem occured while serializing the protocol object");
			e.printStackTrace();
		}
		return objectString;
	}

}
