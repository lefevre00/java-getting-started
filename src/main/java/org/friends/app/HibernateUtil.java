package org.friends.app;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final ThreadLocal<Session> SESSIONS = new ThreadLocal<>();

	public static void closeSession() {
		Session session = SESSIONS.get();
		if (session != null) {
			session.close();
			SESSIONS.remove();
		}
	}

	private static Configuration configuration() {
		String confFile = null;
		switch (ConfHelper.getDeployMode()) {
		case HEROKU:
			confFile = "/hibernate-jndi.cfg.xml";
			break;
		case STANDALONE:
			confFile = "/hibernate-standalone.cfg.xml";
			break;
		case TEST:
		default:
			confFile = "/hibernate-local.cfg.xml";
			break;
		}

		return new Configuration().configure(confFile);
	}

}
