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

import org.friends.app.service.MailException;
import org.friends.app.service.MailSender;
import org.friends.app.service.util.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpMailSender implements MailSender {

	private JavaMailSender javaMailSender;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void send(Mail mail) throws MailException {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mail.getDest());
		msg.setFrom(mail.getFrom());
		msg.setReplyTo(mail.getReplyTo());
		msg.setSubject(mail.getSubject());
		msg.setText(mail.getBody());

		try {
			javaMailSender.send(msg);
		} catch (org.springframework.mail.MailException ex) {
			throw new MailException("Unable to send mail via Smtp", ex);
		}
	}

}
