package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class CoordinatorConfigurationException extends TDSExceptions {

	private static final long serialVersionUID = 8774483245687588923L;

	public CoordinatorConfigurationException(TDSError error, Throwable cause) {
		super(error, cause);
	}
}
