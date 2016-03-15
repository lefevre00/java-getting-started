package org.friends.app;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Messages {

	private static final String MESSAGES_PROPERTIES = "/messages.properties";
	static Properties properties;

	public static String get(String error) {
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
