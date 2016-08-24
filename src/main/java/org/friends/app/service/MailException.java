package org.friends.app.service;

public class MailException extends Exception {

	private static final long serialVersionUID = 1L;

	public MailException(String message) {
		super(message);
	}

	public MailException(String message, Throwable t) {
		super(message, t);
	}
}
