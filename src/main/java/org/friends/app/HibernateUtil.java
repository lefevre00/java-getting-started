package org.friends.app;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);
    
    private static final SessionFactory SESSION_FACTORY = createSessionFactory();
    private static final ThreadLocal<Session> SESSIONS = new ThreadLocal<>();
    
    public static Session getSession() {
        Session session = SESSIONS.get();
        if (session == null) {
            session = SESSION_FACTORY.openSession();
            SESSIONS.set(session);
        }
        return session;
    }
    
    public static void closeSession() {
        Session session = SESSIONS.get();
        if (session != null) {
            session.close();
            SESSIONS.remove();
        }
    }
    
    private static SessionFactory createSessionFactory() {
        try {
            Configuration configuration = configuration();
            configuration.setProperty(AvailableSettings.URL, "jdbc:" + org.friends.app.Configuration.get("DATABASE_URL", "h2:~/test;AUTO_SERVER=TRUE"));
			configuration.setProperty(AvailableSettings.DIALECT, org.friends.app.Configuration.dialect());
            configuration.setProperty(AvailableSettings.USE_QUERY_CACHE, "false");
            configuration.setProperty(AvailableSettings.SHOW_SQL, "true");
            configuration.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.addAnnotatedClass(Place.class);
            configuration.addAnnotatedClass(org.friends.app.model.Session.class);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(serviceRegistryBuilder.build());
        } catch (RuntimeException ex) {
            LOGGER.error("Failed to create session factory");
            throw ex;
        }
    }
 
    private static Configuration configuration() {
        String confFile = "/hibernate-local.cfg.xml";
        if (!org.friends.app.Configuration.development()) {
        	confFile = "/hibernate-jndi.cfg.xml";
        }

        Configuration configuration = new Configuration();
        configuration.configure(confFile);
        return configuration;
    }
    
}
