package com.itt.tds.TDSExceptions.RuntimeExceptions;

import com.itt.tds.TDSExceptions.TDSExceptions;
import com.itt.tds.errorCodes.TDSError;

public class ServerCreationFailedException extends FatalException {

	private static final long serialVersionUID = -5616931546563398666L;

	public ServerCreationFailedException(String message) {
		super(message);
	}

	public ServerCreationFailedException(TDSError errorCode) {
		super(errorCode.toString());
	}

	public ServerCreationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerCreationFailedException(TDSError errorCode, Throwable cause) {
		super(errorCode.toString(), cause);
	}
}
