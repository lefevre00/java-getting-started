package org.friends.app.service.impl;

import static org.friends.app.ConfHelper.getMailServiceLogin;
import static org.friends.app.ConfHelper.getMailServicePassword;

import org.friends.app.ConfHelper;
import org.friends.app.service.MailException;
import org.friends.app.service.MailSender;
import org.friends.app.service.util.Mail;
import org.jboss.logging.Logger;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

/**
 * Mail sender agent base on SendGrid. SendGrid is easily pluggable with Heroku.
 * 
 * @see http://sendgrid.com/
 */
public class SendGridMailSender implements MailSender {

	@Override
	public void send(Mail mail) throws MailException {
		if (!isEnable()) {
			Logger.getLogger(getClass()).info("Mail sending disabled.");
			return;
		}

		SendGrid.Email email = new SendGrid.Email();
		email.addTo(mail.getDest());
		email.setSubject(mail.getSubject());
		email.setText(mail.getBody());

		SendGrid sendgrid = new SendGrid(getMailServiceLogin(), getMailServicePassword());
		email.setFrom("takemyplace@heroku.com");
		email.setReplyTo("no-reply@heroku.com");

		try {
			SendGrid.Response response = sendgrid.send(email);
			if (!response.getStatus()) {
				Logger.getLogger(getClass()).warn("Fail to send mail : " + response.getMessage());
			}
		} catch (SendGridException e) {
			throw new MailException("Cannot send mail via SendGrid", e);
		}
	}

	private boolean isEnable() {
		String value = ConfHelper.get(ConfHelper.MAIL_ENABLE, "false");
		return Boolean.parseBoolean(value);
	}
}
