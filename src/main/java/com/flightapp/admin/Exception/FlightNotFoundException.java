package com.flightapp.admin.Exception;

public class FlightNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlightNotFoundException() {
		super();
	}

	public FlightNotFoundException(final String message) {
		super(message);
	}

}
