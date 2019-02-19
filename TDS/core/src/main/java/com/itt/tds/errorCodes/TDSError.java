package com.itt.tds.errorCodes;

public enum TDSError {
	

	// Common error 0-200
	// Client error 201-400
	// Coordinator error 401-600
	// Node error 601-800
	NO_ERROR(0, "No error"), 
	DESTINATION_SERVER_NOT_FOUND(1, "Destination Server not found"),
	FILE_NOT_FOUND(2, "File not found"),
	FAILED_TO_CREATE_CONFIG(3, "Failed to create configuration file"),
	UNKNOWN_HOST(4, "Unable to read host name"),
	UNKNOWN_IP(5, "Unable to read IP of the device"),
	FAILED_TO_READ_FILE(6, "Failed to read file"),
	INVALID_JSON_STRING(7, "Invalid JSON String"),
	INVALID_XML_STRING(8, "Invalid XML string"),
	UNABLE_TO_SERIALIZE(9, "Unable to serialize the object"),
	UNABLE_TO_FIND_CONFIG(10, "Unable to find configuration file. Run generate-config command"),
	INVALID_REQUEST_METHOD(11, "Requested method is invalid"),
	
	CLIENT_NOT_REGISTERED(401, "Client did not registered any task"),
	INVALID_TASK_ID(402, "Invalid Task ID");


	private final int code;
	private final String description;

	private TDSError(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Error : " + code + " " + description;
	}
}
