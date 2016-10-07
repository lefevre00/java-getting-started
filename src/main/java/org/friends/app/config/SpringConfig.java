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
package org.friends.app.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.friends.app.ConfHelper;
import org.friends.app.DeployMode;
import org.friends.app.service.MailSender;
import org.friends.app.service.impl.ConsoleMailSender;
import org.friends.app.service.impl.SendGridMailSender;
import org.friends.app.service.impl.SmtpMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spark.utils.StringUtils;

@Configuration
public class SpringConfig {

	@Bean
	public MailSender getMailSender() {
		DeployMode mode = ConfHelper.getDeployMode();
		switch (mode) {
		case HEROKU:
			return new SendGridMailSender();
		case STANDALONE:
			if (sendMailDefined()) {
				return new SmtpMailSender();
			}
		default:
			return new ConsoleMailSender();
		}
	}

	private boolean sendMailDefined() {
		try {
			Context context = new InitialContext();
			if (context == null)
				return false;
			String sendMail = (String) context.lookup("java:comp/env/sendMail");
			if (StringUtils.isEmpty(sendMail)) {
				return false;
			} else {
				return true;
			}
		} catch (NamingException e) {
			return false;
		}
	}
}
