package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class InvalidSerializedStringException extends TDSExceptions{

	private static final long serialVersionUID = 1735162310983864618L;

	public InvalidSerializedStringException(String message) {
        super(message);
    }
	
	public InvalidSerializedStringException(TDSError errorCode) {
		super(errorCode.toString());
    }

    public InvalidSerializedStringException(String message, Throwable cause) {
    	super(message, cause);
    }

    public InvalidSerializedStringException(TDSError errorCode, Throwable cause) {
    	super(errorCode.toString(), cause);
    }
}
