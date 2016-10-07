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
