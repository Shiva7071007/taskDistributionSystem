package com.itt.tds.TDSExceptions.CoreException.CommException;

import com.itt.tds.TDSExceptions.TDSExceptions;
import com.itt.tds.errorCodes.TDSError;

public class InvalidSerializedStringException extends TDSExceptions{

	private static final long serialVersionUID = 1735162310983864618L;

	public InvalidSerializedStringException(TDSError error, Throwable cause) {
		super(error, cause);
	}
}
