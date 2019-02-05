package com.itt.tds.exceptions;

import java.sql.SQLException;

public class DBConnectionException extends SQLException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBConnectionException (SQLException e) {
		System.out.println("Exception: " + e.getMessage() + " Thrown by: " + e.getClass().getSimpleName());
	}
}
