package com.itt.tds.logging;

import org.apache.log4j.Logger;

public class TDSLogger {

	public Logger getLogger () {
		String className = new Exception().getStackTrace()[1].getClassName();
		Logger logger = Logger.getLogger(className);
		return logger;
	}
}
