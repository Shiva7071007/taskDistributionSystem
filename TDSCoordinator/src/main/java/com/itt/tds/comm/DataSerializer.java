package com.itt.tds.comm;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DataSerializer {

	/**
	 * @param protocolObect
	 * @param rawData
	 * @throws JsonProcessingException
	 */
	public static String Serialize(Object obj) throws JsonProcessingException {
		String objectString = "";
		ObjectMapper mapper = new ObjectMapper();
		objectString = mapper.writeValueAsString(obj);
		return objectString;
	}

	/**
	 * @param rawData
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static Object DeSerialize(String rawData) throws JsonParseException, JsonMappingException, IOException {
		Object obj = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonObject jObject = new Gson().fromJson(rawData, JsonObject.class);
		String protocolType = jObject.get("protocolType").getAsString();
		if (protocolType.equalsIgnoreCase("request")) {
			TDSRequest reqObject = mapper.readValue(rawData, TDSRequest.class);
			obj = reqObject;
		} else if (protocolType.equalsIgnoreCase("response")) {
			TDSResponse resObject = mapper.readValue(rawData, TDSResponse.class);
			obj = resObject;
		}
		return obj;
	}
}
