package com.itt.tds.TDSExceptions.CoreException.CommException;

import com.itt.tds.TDSExceptions.TDSExceptions;
import com.itt.tds.errorCodes.TDSError;

public class FailedServerCreationException extends TDSExceptions {

	private static final long serialVersionUID = -5616931546563398666L;

	public FailedServerCreationException(TDSError error, Throwable cause) {
		super(error, cause);
	}

}
