package org.friends.app;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.friends.app.view.route.LoginRoute;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL92Dialect;

import spark.utils.StringUtils;

public class ConfHelper {

	public final static String PORT = "PORT";
	public final static String MAIL_ENABLE = "MAIL_ENABLE";

	public final static String COOKIE = "takemyplace";
	public final static int COOKIE_DURATION = 86400; // One day
	public static final String EMAIL_CONTACT = "contact@takemaplace.fr";

	private static final String APPLICATION_PROPERTIES = "/application.properties";
	public static final String ADMIN_MAIL;
	public static final String ADMIN_PWD_MD5;
	static {
		// Données login admin dans fichier properties
		Properties tmp = new Properties();
		try {
			tmp.load(LoginRoute.class.getResourceAsStream(APPLICATION_PROPERTIES));
		} catch (IOException e) {
			System.out.println("erreur lecture application.properties");
		}
		ADMIN_MAIL = tmp.getProperty("admin.email");
		ADMIN_PWD_MD5 = getEncryptedMD5Password(tmp.getProperty("admin.password"));
	}

	public static String getMailServiceLogin() {
		return System.getenv("SENDGRID_USERNAME");
	}

	public static String getMailServicePassword() {
		return System.getenv("SENDGRID_PASSWORD");
	}

	public static String getMailTeam() {
		return get("MAIL_TEAM", "contact@takemyplace.fr");
	}

	public static Integer getPort() {
		String port = System.getenv(PORT);
		if (port == null)
			port = System.getProperty(PORT);
		if (port == null)
			throw new RuntimeException("Port not defined");
		return Integer.valueOf(port);
	}

	public static DeployMode getDeployMode() {
		String property = System.getProperty(DeployMode.PROPERTY);
		if (property == null) {
			return DeployMode.STANDALONE;
		}

		DeployMode mode = DeployMode.valueOf(property);
		if (mode != null) {
			return mode;
		}

		return DeployMode.TEST;
	}

	public static String dialect() {
		return DeployMode.TEST.equals(getDeployMode()) ? H2Dialect.class.getName()
				: PostgreSQL92Dialect.class.getName();
	}

	/**
	 * Get configuration, first in environment, then in property.
	 * 
	 * @param propertyName
	 */
	public static String get(String propertyName, String defaultValue) {
		String value = System.getenv(propertyName);
		if (!StringUtils.isEmpty(value))
			return value;
		return System.getProperty(propertyName, defaultValue);
	}

	public static String dbUrl() {
		String url = null;
		switch (getDeployMode()) {
		case HEROKU:
			try {
				URI dbUri = new URI(System.getenv("DATABASE_URL"));

				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?user="
						+ username + "&password=" + password;
			} catch (URISyntaxException e) {
				throw new RuntimeException("unable to get Datasource url", e);
			}
			break;
		case STANDALONE:
			// url =
			// "jdbc:postgresql://intramdm-dev.amdm.local:5432/lar?user=intrausr&password=IntraPS";
			url = "jdbc:postgresql://localhost:5432/parking?user=postgres&password=admin";
			break;
		default:
			url = "jdbc:h2:~/test;AUTO_SERVER=TRUE";
			break;
		}

		return url;
	}

	private static String getEncryptedMD5Password(String pass) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException ex) {

		}
		return sb.toString();
	}
	
	public static String complementUrl(){
		return DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? ".." : "";
	}
}
