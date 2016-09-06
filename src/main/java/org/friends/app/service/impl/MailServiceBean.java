package org.friends.app.service.impl;

import static org.friends.app.ConfHelper.getMailTeam;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.friends.app.model.User;
import org.friends.app.service.MailException;
import org.friends.app.service.MailSender;
import org.friends.app.service.MailService;
import org.friends.app.service.util.MailBuilder;
import org.friends.app.view.route.Routes;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spark.utils.StringUtils;

@Service
public class MailServiceBean implements MailService {

	private static final String MAIL_BONJOUR = "Bonjour\n\n";
	private static final String MAIL_SIGNATURE = "\n\nCordialement,\nL'équipe EcoParking.";

	@Autowired
	private MailSender sender;

	@Override
	public void sendWelcome(User user, String applicationUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append(MAIL_BONJOUR).append("Vous venez de vous enregistrer sur le site de partage du parking Mezzo.\n")
				.append("Afin de finaliser votre inscription, vous devez vous rendre à l'adresse indiquée ci-dessous pour valider votre email.\n")
				.append(applicationUrl).append(Routes.TOKEN_VALIDATION).append('?').append(Routes.PARAM_TOKEN_VALUE)
				.append('=').append(user.getTokenMail()).append(MAIL_SIGNATURE);

		MailBuilder mb = MailBuilder.get().addTo(user.getEmailAMDM()).setSubject("Bienvenue @ EcoParking")
				.setText(sb.toString());
		doSend(mb);
	}

	@Override
	public void sendLostPassword(User user, String applicationUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append(MAIL_BONJOUR).append("Vous venez de demander la modification de votre mot de passe.\n")
				.append("Pour cela, nous vous invitons à vous rendre à l'adresse ci-dessous pour définir votre nouveau mot de passe.\n")
				.append(applicationUrl).append(Routes.PASSWORD_NEW).append('?').append(Routes.PARAM_TOKEN_VALUE)
				.append('=').append(user.getTokenPwd()).append(MAIL_SIGNATURE);

		MailBuilder mb = MailBuilder.get().addTo(user.getEmailAMDM()).setSubject("EcoParking, problème de connexion")
				.setText(sb.toString());

		doSend(mb);
	}

	@Override
	public void sendContact(String nom, String mail, String message) {
		StringBuilder sb = new StringBuilder().append("Nom : ").append(nom).append("\n Mail :").append(mail)
				.append("\n Message : \n").append(message);

		MailBuilder mb = MailBuilder.get().addTo(getMailTeam()).setSubject("Message depuis le formulaire de contact")
				.setText(sb.toString());

		doSend(mb);
	}

	private void doSend(MailBuilder mb) {
		try {
			Context context = new InitialContext();
			String sendMail = (String)context.lookup("java:comp/env/sendMail");
			if(StringUtils.isEmpty(sendMail)) {
				System.out.println("SendMail pas définit");
				sendMail = "false";
			}
			if(Boolean.valueOf(sendMail)) {
					sender.send(mb.build());
			} else {
				new ConsoleMailSender().send(mb.build());
			}
		} catch (MailException | NamingException e) {
			LoggerFactory.logger(getClass()).error("Unable send mail");
			e.printStackTrace();
			new RuntimeException("Unable to send mail", e);
		}
	}
}
