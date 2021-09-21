package com.flightapp.admin.Exception;

public class FlightAlreadyFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlightAlreadyFoundException() {
		super();
	}

	public FlightAlreadyFoundException(final String message) {
		super(message);
	}
}
