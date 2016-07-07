package org.friends.app.service.impl;

import static org.friends.app.Configuration.getMailServiceLogin;
import static org.friends.app.Configuration.getMailServicePassword;
import static org.friends.app.Configuration.getMailTeam;

import org.friends.app.Configuration;
import org.friends.app.model.User;
import org.friends.app.service.MailService;
import org.friends.app.view.route.Routes;
import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@Service
public class MailServiceBean implements MailService {

	private static final String MAIL_BONJOUR = "Bonjour\n\n";
	private static final String MAIL_SIGNATURE = "\n\nCordialement,\nL'équipe EcoParking.";

	private void sendMail(SendGrid.Email email) {
		String value = Configuration.get(Configuration.MAIL_ENABLE, "false");

		if (Boolean.parseBoolean(value)) {
			SendGrid sendgrid = new SendGrid(getMailServiceLogin(), getMailServicePassword());
			email.setFrom("takemyplace@heroku.com");
			email.setReplyTo("no-reply@heroku.com");

			try {
				SendGrid.Response response = sendgrid.send(email);
				if (!response.getStatus()) {
					java.util.logging.Logger.getLogger("mail").warning("fail to send mail : " + response.getMessage());
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
			System.out.println("-- Text start");
			System.out.println(email.getText());
			System.out.println("-- Text end");
		}
	}

	@Override
	public void sendWelcome(User user, String applicationUrl) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(user.getEmailAMDM());
		email.setSubject("Bienvenue @ EcoParking");
		StringBuilder sb = new StringBuilder();
		sb.append(MAIL_BONJOUR).append("Vous venez de vous enregistrer sur le site de partage du parking Mezzo.\n")
				.append("Afin de finaliser votre inscription, vous devez vous rendre à l'adresse indiquée ci-dessous pour valider votre email.\n")
				.append(applicationUrl).append(Routes.TOKEN_VALIDATION).append('?').append(Routes.PARAM_TOKEN_VALUE)
				.append('=').append(user.getTokenMail()).append(MAIL_SIGNATURE);
		email.setText(sb.toString());

		sendMail(email);
	}

	@Override
	public void sendLostPassword(User user, String applicationUrl) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(user.getEmailAMDM());
		email.setSubject("EcoParking, problème de connexion");
		StringBuilder sb = new StringBuilder();
		sb.append(MAIL_BONJOUR).append("Vous venez de demander la modification de votre mot de passe.\n")
				.append("Pour cela, nous vous invitons à vous rendre à l'adresse ci-dessous pour définir votre nouveau mot de passe.\n")
				.append(applicationUrl).append(Routes.PASSWORD_NEW).append('?').append(Routes.PARAM_TOKEN_VALUE)
				.append('=').append(user.getTokenPwd()).append(MAIL_SIGNATURE);
		email.setText(sb.toString());

		sendMail(email);
	}

	@Override
	public void sendContact(String nom, String mail, String message) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(getMailTeam());
		email.setSubject("Message depuis le formulaire de contact");
		StringBuilder sb = new StringBuilder();
		sb.append("Nom : ").append(nom).append("\n Mail :").append(mail).append("\n Message : \n").append(message);
		email.setText(sb.toString());
		sendMail(email);
	}
}
