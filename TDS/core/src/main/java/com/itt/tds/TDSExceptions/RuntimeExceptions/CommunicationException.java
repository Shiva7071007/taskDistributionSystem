package com.itt.tds.TDSExceptions.RuntimeExceptions;

import com.itt.tds.errorCodes.TDSError;

public class CommunicationException extends FatalException {

	private static final long serialVersionUID = -6913209636186848618L;

	public CommunicationException (String message) {
		super(message);
	}

	public CommunicationException (TDSError errorCode) {
		super(errorCode.toString());
	}

	public CommunicationException (String message, Throwable cause) {
		super(message, cause);
	}

	public CommunicationException (TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}
}
