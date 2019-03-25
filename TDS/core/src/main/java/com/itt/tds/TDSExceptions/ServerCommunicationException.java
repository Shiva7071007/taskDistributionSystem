package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class ServerCommunicationException extends TDSException {

	private static final long serialVersionUID = 880390603275750700L;

	public ServerCommunicationException(String message) {
		super(message);
	}

	public ServerCommunicationException(TDSError errorCode) {
		super(errorCode.toString());
	}

	public ServerCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerCommunicationException(TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}

}
