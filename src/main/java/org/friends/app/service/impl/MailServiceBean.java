/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.friends.app.service.impl;

import static org.friends.app.ConfHelper.getMailTeam;

import org.friends.app.ConfHelper;
import org.friends.app.model.User;
import org.friends.app.service.MailException;
import org.friends.app.service.MailSender;
import org.friends.app.service.MailService;
import org.friends.app.service.util.MailBuilder;
import org.friends.app.view.route.Routes;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			sender.send(mb.build());
		} catch (MailException e) {
			LoggerFactory.logger(getClass()).error("Unable send mail");
			new RuntimeException("Unable to send mail", e);
		}
	}

	@Override
	public void sendInformation(User user, String applicationUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append(MAIL_BONJOUR).append("Votre compte EcoParking a été créé.\n")
			.append(".\nAfin de finaliser votre inscription, vous devez vous rendre à l'adresse indiquée ci-dessous pour valider votre email");
				if(ConfHelper.INSCRIPTION_LIBRE) sb.append(" et chosir votre mot de passe");
		sb.append(".\n").append(applicationUrl).append(Routes.REGISTER).append('?').append(Routes.PARAM_TOKEN_VALUE).append('=')
			.append(user.getTokenMail()).append('&').append(Routes.PARAM_EMAIL_VALUE).append("=")
			.append(user.getEmailAMDM()).append('&').append(Routes.PARAM_PLACE_NUMBER_VALUE).append("=")
			.append(user.getPlaceNumber() != null ? user.getPlaceNumber() : "").append(MAIL_SIGNATURE);

		MailBuilder mb = MailBuilder.get().addTo(user.getEmailAMDM()).setSubject("Bienvenue @ EcoParking")
				.setText(sb.toString());
		doSend(mb);

	}

	@Override
	public void sendInformationChangementPlace(User user, Integer oldPlace) {
		StringBuilder sb = new StringBuilder();
		sb.append(MAIL_BONJOUR);
		if (oldPlace != null) {
			// l'utilisateur avait une place
			if (user.getPlaceNumber() == null) {
				sb.append("La place de parking n°").append(oldPlace).append(" a été attribuée à une autre personne.");
			} else {
				sb.append("Nous avons du échanger la place de parking n°").append(oldPlace).append(" par la place n°")
						.append(user.getPlaceNumber());
			}
		} else {
			// L'utilisateur n'avait pas de place
			if (user.getPlaceNumber() != null) {
				sb.append("Nous sommes heureux de vous attibuer la place de parking n°").append(user.getPlaceNumber())
						.append(".");
			}
		}
		sb.append(MAIL_SIGNATURE);

		MailBuilder mb = MailBuilder.get().addTo(user.getEmailAMDM()).setSubject("Bienvenue @ EcoParking")
				.setText(sb.toString());
		doSend(mb);
	}
}
