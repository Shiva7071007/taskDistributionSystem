package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class TDSProtocolSerializationException extends TDSExceptions {

	private static final long serialVersionUID = 1L;

	public TDSProtocolSerializationException(String message) {
        super(message);
    }
	
	public TDSProtocolSerializationException(TDSError errorCode) {
		super(errorCode.toString());
    }

    public TDSProtocolSerializationException(String message, Throwable cause) {
    	super(message, cause);
    }

    public TDSProtocolSerializationException(TDSError errorCode, Throwable cause) {
    	super(errorCode.toString(), cause);
    }
}
