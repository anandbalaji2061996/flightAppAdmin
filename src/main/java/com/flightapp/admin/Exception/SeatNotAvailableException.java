package com.flightapp.admin.Exception;

public class SeatNotAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeatNotAvailableException() {
		super();
	}

	public SeatNotAvailableException(final String message) {
		super(message);
	}
}
