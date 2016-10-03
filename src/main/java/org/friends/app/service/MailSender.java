package org.friends.app.service;

import org.friends.app.service.util.Mail;

/**
 * Interface providing abstraction for outgoing mail agent.
 */
public interface MailSender {

	/**
	 * Send the given mail
	 * 
	 * @param build
	 * @throws MailException
	 */
	void send(Mail mail) throws MailException;
}
