package org.friends.app.servletcontext;

import org.friends.app.Configuration;
import org.friends.app.view.Application;

import spark.servlet.SparkApplication;

/**
 * Implémentation de {@link SparkApplication} pour pouvoir déployer l'application sur un moteur de servlet 
 *
 */
public class ApplicationServlet implements SparkApplication {

	@Override
	public void init() {
		System.setProperty("PORT", "8080");
		System.setProperty(Configuration.DEPLOY_MODE, Configuration.DEPLOY_MODE_STANDALONE);
		System.setProperty("MAIL_TEAM", "contact@takemyplace.fr");
		new Application().start();
	}

}
