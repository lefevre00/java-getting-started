package org.friends.app;

import java.net.URI;
import java.net.URISyntaxException;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL92Dialect;
import org.springframework.context.annotation.ComponentScan;

import spark.utils.StringUtils;

@org.springframework.context.annotation.Configuration("application")
@ComponentScan("org.friends.app.*")
public class Configuration {

	public final static String PORT = "PORT";
	public final static String DEPLOY_MODE = "PARKING_MODE";
	public final static String MAIL_ENABLE = "MAIL_ENABLE";
	
	public final static String COOKIE = "takemyplace";
	public final static int COOKIE_DURATION = 86400 ; // One day
	public static final String EMAIL_CONTACT = "contact@takemaplace.fr";	
	
	public static String getMailServiceLogin() {
		return System.getenv("SENDGRID_USERNAME");
	}

	public static String getMailServicePassword() {
		return System.getenv("SENDGRID_PASSWORD");
	}
	
	public static String getMailTeam(){
		return get("MAIL_TEAM", "");
	}

	public static Integer getPort() {
		String port = System.getenv(PORT);
		if (port == null)
			port = System.getProperty(PORT);
		if (port == null)
			throw new RuntimeException("Port not defined");
		return Integer.valueOf(port);
	}

	public static boolean development() {
		return System.getProperty(DEPLOY_MODE) != null;
	}
	
	public static String dialect() {
		return development() ? H2Dialect.class.getName() : PostgreSQL92Dialect.class.getName();
	}
	
	/**
	 * Get configuration, first in environment, then in property.
	 * @param propertyName
	 */
	public static String get(String propertyName, String defaultValue) {
		String value = System.getenv(propertyName);
		if (!StringUtils.isEmpty(value))
			return value;
		return System.getProperty(propertyName, defaultValue);
	}

	public static String databaseUrl() {
		return development() ? H2Dialect.class.getName() : PostgreSQL92Dialect.class.getName();
	}

	public static String dbUrl() {
		String url = "jdbc:h2:~/test;AUTO_SERVER=TRUE"; 
		if (!development()) {
			try {
				URI dbUri = new URI(System.getenv("DATABASE_URL"));

				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
				+ "?user=" + username + "&password=" + password;
			} catch (URISyntaxException e) {
				throw new RuntimeException("unable to get Datasource url", e);
			}
		}
		return url;
	}
}
