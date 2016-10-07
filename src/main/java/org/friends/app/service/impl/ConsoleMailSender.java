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
