package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class InvalidTDSProtocolObjectException extends TDSExceptions {

	private static final long serialVersionUID = 6091642988965294830L;

	public InvalidTDSProtocolObjectException(String message) {
		super(message);
	}

	public InvalidTDSProtocolObjectException(TDSError errorCode) {
		super(errorCode.toString());
	}

	public InvalidTDSProtocolObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTDSProtocolObjectException(TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}
}
