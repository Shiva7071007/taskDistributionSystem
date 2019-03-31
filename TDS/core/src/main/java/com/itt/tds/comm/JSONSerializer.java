package com.itt.tds.comm;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.tds.TDSExceptions.TDSProtocolSerializationException;
import com.itt.tds.comm.TDSProtocol;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class JSONSerializer implements TDSSerializer {

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public TDSProtocol DeSerialize(String data) throws TDSProtocolSerializationException {
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
				throw new TDSProtocolSerializationException(TDSError.INVALID_JSON_STRING);
			}
		} catch (IOException e) {
			throw new TDSProtocolSerializationException(TDSError.INVALID_JSON_STRING, e);
		}

		return tdsProtocolObj;
	}

	@Override
	public String Serialize(TDSProtocol protocol) throws TDSProtocolSerializationException {
		String objectString = "";
		ObjectMapper mapper = new ObjectMapper();

		try {
			objectString = mapper.writeValueAsString(protocol);
		} catch (JsonProcessingException e) {
			throw new TDSProtocolSerializationException(TDSError.UNABLE_TO_SERIALIZE, e);
		}

		return objectString;
	}

}
