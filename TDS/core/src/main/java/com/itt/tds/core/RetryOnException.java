package com.itt.tds.core;

import org.apache.log4j.Logger;

import com.itt.tds.TDSExceptions.TDSException;
import com.itt.tds.logging.TDSLogger;

/**
 * Encapsulates retry-on-exception operations
 */
public class RetryOnException {
	public static final int DEFAULT_RETRIES = 5;
	public static final long DEFAULT_TIME_TO_WAIT_MS = 2 * 1000;
	
	static Logger logger = new TDSLogger().getLogger();

	private int numRetries;
	private long timeToWaitMS;

	public RetryOnException() {
		this(DEFAULT_RETRIES, DEFAULT_TIME_TO_WAIT_MS);
	}

	public RetryOnException(int _numRetries, long _timeToWaitMS) {
		numRetries = _numRetries;
		timeToWaitMS = _timeToWaitMS;
	}

	/**
	 * shouldRetry Returns true if a retry can be attempted.
	 * 
	 * @return True if retries attempts remain; else false
	 */
	private boolean shouldRetry() {
		return (numRetries >= 0);
	}

	/**
	 * waitUntilNextTry Waits for timeToWaitMS. Ignores any interrupted exception
	 */
	private void waitUntilNextTry() {
		try {
			Thread.sleep(timeToWaitMS);
		} catch (InterruptedException iex) {
			logger.error("Exception happening while waiting for retrying method", iex);
		}
	}

	/**
	 * exceptionOccurred Call when an exception has occurred in the block. If the
	 * retry limit is exceeded, throws an exception. Else waits for the specified
	 * time.
	 * 
	 * @throws Exception
	 */
	public void exceptionOccurred(String message, Exception e) throws TDSException {
		numRetries--;
		if (!shouldRetry()) {
			logger.error("Retry limit exceeded.");
			throw new TDSException(message, e);
		}
		waitUntilNextTry();
	}
}
