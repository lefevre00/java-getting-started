package org.friends.app.service.impl;

import static org.friends.app.Configuration.getMailServiceLogin;
import static org.friends.app.Configuration.getMailServicePassword;

import org.friends.app.Configuration;
import org.friends.app.model.User;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class MailServiceBean {

	private void sendMail(SendGrid.Email email) {
		String value = Configuration.get(Configuration.MAIL_ENABLE, "false");

		if (Boolean.parseBoolean(value)) {
			SendGrid sendgrid = new SendGrid(getMailServiceLogin(), getMailServicePassword());
			email.setFrom("takemyplace@heroku.com");
			email.setReplyTo("no-reply@heroku.com");

			try {
				SendGrid.Response response = sendgrid.send(email);
				if (response.getStatus()) {
					java.util.logging.Logger.getLogger("mail").warning("fail to send mail");
				}
			} catch (SendGridException e) {
				System.out.println(e);
			}
		} else {
			System.out.println("Fake '" + email.getSubject() + "' mail send to ");
			String[] dests = email.getTos();
			for (int i = 0; i < dests.length; i++) {
				System.out.println("\t " + dests[i]);
			}
		}
	}

	public void sendWelcome(User user) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(Configuration.get("MAIL_TEST", "foo@bar.null"));
		email.setSubject("Bienvenue @ TakeMyPlace");
		email.setText("Texte de bienvenue avec le token qu'il faut mettre dedans.");
		sendMail(email);
	}

	public void sendLostPassword(User user) {
		throw new UnsupportedOperationException("TODO");
	}
}
