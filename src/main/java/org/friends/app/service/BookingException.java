package org.friends.app.service;

/**
 * Indicate the booking could not be finalized.
 * @author michael lefevre
 */
public class BookingException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookingException(String message) {
		super(message);
	}
}
