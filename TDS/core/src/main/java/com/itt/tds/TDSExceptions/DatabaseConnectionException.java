package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class DatabaseConnectionException extends TDSException {

	private static final long serialVersionUID = -466017099742018972L;

	public DatabaseConnectionException(String message) {
		super(message);
	}

	public DatabaseConnectionException(TDSError errorCode) {
		super(errorCode.toString());
	}

	public DatabaseConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseConnectionException(TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}
}
