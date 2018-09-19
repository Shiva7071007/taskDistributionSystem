package com.itt.tds.exceptions;

import java.sql.SQLException;

public class DBConnectionException extends SQLException{

	public DBConnectionException (SQLException e) {
		System.out.println("Exception: " + e.getMessage() + " Thrown by: " + e.getClass().getSimpleName());
	}
}
