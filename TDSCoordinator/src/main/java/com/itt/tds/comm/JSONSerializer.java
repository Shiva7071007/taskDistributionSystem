package com.itt.tds.comm;

import java.io.IOException;
import com.itt.tds.comm.CommConstants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSONSerializer implements TDSSerializer {

	@Override
	public TDSProtocol DeSerialize(String data) {
		TDSProtocol tdsProtocolObj = null;
		ObjectMapper mapper = new ObjectMapper();
		
		JsonObject jObject = new Gson().fromJson(data, JsonObject.class);
		String protocolType = jObject.get(CommConstants.PROTOCOL_TYPE).getAsString();
		
		try {
			if (protocolType.equalsIgnoreCase(CommConstants.REQUEST)) {
				TDSProtocol reqObject = null;
				reqObject = mapper.readValue(data, TDSRequest.class);
				tdsProtocolObj = reqObject;
			} else if (protocolType.equalsIgnoreCase(CommConstants.RESPONSE)) {
				TDSResponse resObject = null;
				resObject = mapper.readValue(data, TDSResponse.class);
				tdsProtocolObj = resObject;
			} else {
				System.err.println("Invalid JSON string for protocol object");
			}
		} catch (IOException e) {
			System.err.println("Invalid json String");
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
