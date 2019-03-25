package com.itt.tds.TDSExceptions;

import com.itt.tds.errorCodes.TDSError;

public class SocketReadWriteException extends TDSException {
	
	private static final long serialVersionUID = 5086710947909736080L;

	public SocketReadWriteException(String message) {
        super(message);
    }
	
	public SocketReadWriteException(TDSError errorCode) {
		super(errorCode.toString());
    }

    public SocketReadWriteException(String message, Throwable cause) {
    	super(message, cause);
    }

    public SocketReadWriteException(TDSError errorCode, Throwable cause) {
    	super(errorCode.toString(), cause);
    }

}
