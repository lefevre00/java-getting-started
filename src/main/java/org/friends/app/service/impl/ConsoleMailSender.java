package org.friends.app.service.impl;

import org.friends.app.service.MailException;
import org.friends.app.service.MailSender;
import org.friends.app.service.util.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dummy sender, only dumping mail content to System.out.
 */
public class ConsoleMailSender implements MailSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleMailSender.class);

	@Override
	public void send(Mail email) throws MailException {
		LOGGER.warn("\n>>> Mail send start");
		LOGGER.warn("Header");
		LOGGER.warn("\tFrom : " + email.getFrom());
		for (String s : email.getDest()) {
			LOGGER.warn("\tDest : " + s);
		}
		LOGGER.warn("\tSubject : '" + email.getSubject() + "'");
		LOGGER.warn("-- Body start");
		LOGGER.warn(email.getBody());
		LOGGER.warn("-- Body end");
		LOGGER.warn("<<< Mail send end\n");
	}
}
