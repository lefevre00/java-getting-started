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
package org.friends.app;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Messages {

	private static final String MESSAGES_PROPERTIES = "/messages.properties";
	static Properties properties;

	public static synchronized String get(String error) {
		if (properties == null)
			loadProperties();
		return properties.getProperty(error);
	}

	private static void loadProperties() {
		Properties tmp = new Properties();
		try {
			tmp.load(Messages.class.getResourceAsStream(MESSAGES_PROPERTIES));
		} catch (IOException e) {
			Logger.getLogger("properties").log(Level.CONFIG, "file not loaded : " + MESSAGES_PROPERTIES);
		}
		properties = tmp;
	}
}
