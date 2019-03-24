package com.itt.tds.comm;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.itt.tds.TDSExceptions.TDSProtocolSerializationException;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class XMLSerializer implements TDSSerializer {

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public TDSProtocol DeSerialize(String data) throws TDSProtocolSerializationException {

		logger.debug("Serialized data ==> " + data);

		XmlMapper xmlMapper = new XmlMapper();
		TDSProtocol tdsProtocolObj = null;
		try {
			tdsProtocolObj = xmlMapper.readValue(data, TDSProtocol.class);

			String protocolType = tdsProtocolObj.getProtocolType();

			if (protocolType.equalsIgnoreCase(REQUEST)) {
				TDSRequest reqObject = null;
				reqObject = xmlMapper.readValue(data, TDSRequest.class);
				tdsProtocolObj = reqObject;

			} else if (protocolType.equalsIgnoreCase(RESPONSE)) {
				TDSResponse resObject = null;
				resObject = xmlMapper.readValue(data, TDSResponse.class);
				tdsProtocolObj = resObject;

			} else {
				throw new TDSProtocolSerializationException(TDSError.INVALID_XML_STRING);
			}
		} catch (Exception e) {
			throw new TDSProtocolSerializationException(TDSError.INVALID_XML_STRING, e.getCause());
		}
		return tdsProtocolObj;
	}

	@Override
	public String Serialize(TDSProtocol protocol) throws TDSProtocolSerializationException {
		XmlMapper xmlMapper = new XmlMapper();
		String objString = "";

		try {
			objString = xmlMapper.writeValueAsString(protocol);
		} catch (JsonProcessingException e) {
			throw new TDSProtocolSerializationException(TDSError.UNABLE_TO_SERIALIZE, e);
		}
		return objString;
	}

}
