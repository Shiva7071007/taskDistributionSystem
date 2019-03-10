package com.itt.tds.TDSExceptions.CoreException.CommException;

import com.itt.tds.TDSExceptions.TDSExceptions;
import com.itt.tds.errorCodes.TDSError;

public class InvalidTDSProtocolObjectException extends TDSExceptions{

	private static final long serialVersionUID = 6091642988965294830L;

	public InvalidTDSProtocolObjectException(TDSError error, Throwable cause) {
		super(error, cause);
	}

}
