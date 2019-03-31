package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class DatabaseTransactionException extends TDSException{

	private static final long serialVersionUID = -2177657551067593073L;

	public DatabaseTransactionException(String message) {
		super(message);
	}

	public DatabaseTransactionException(TDSError errorCode) {
		super(errorCode.toString());
	}

	public DatabaseTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseTransactionException(TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}
}
