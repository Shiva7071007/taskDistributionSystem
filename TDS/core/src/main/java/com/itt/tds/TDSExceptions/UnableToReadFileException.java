package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class UnableToReadFileException extends TDSExceptions {

	private static final long serialVersionUID = 5921620873778601046L;

	public UnableToReadFileException(String message) {
		super(message);
	}

	public UnableToReadFileException(TDSError errorCode) {
		super(errorCode.toString());
	}

	public UnableToReadFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToReadFileException(TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}
}
