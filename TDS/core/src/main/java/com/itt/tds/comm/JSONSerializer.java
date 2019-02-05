package com.itt.tds.comm;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.tds.comm.TDSProtocol;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class JSONSerializer implements TDSSerializer {

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public TDSProtocol DeSerialize(String data)  {
		TDSProtocol tdsProtocolObj = null;
		logger.debug("Serialized data ==> " + data);
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
			logger.error(TDSError.INVALID_JSON_STRING.toString());
			logger.trace(e);
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
			logger.error(TDSError.UNABLE_TO_SERIALIZE.toString());
			logger.trace(e);
		}
		return objectString;
	}

}
