package org.friends.app;

import java.sql.Connection;
import java.sql.DriverManager;

import org.friends.app.dao.UserDao;
import org.friends.app.model.User;
import org.friends.app.view.Application;

public class StartParking {


	public static void main(String[] args) {
		System.setProperty("PORT", "8080");
		System.setProperty(Configuration.DEPLOY_MODE, "dev");

		new Application() {
			protected Connection getConnection() throws java.sql.SQLException, java.net.URISyntaxException {
				try {
					Class.forName("org.h2.Driver");
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Could not find H2");
				}
				Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
				return con;
			};
			
			public void start() {
				super.start();
				initData();
			}
		}.start();
	}

	private static void initData() {
		UserDao userDao = new UserDao();
		userDao.persist(new User("abdel.tamditi@amdm.fr", "at" , 133));
		userDao.persist(new User("william.verdeil@amdm.fr", "wv", 141));
		userDao.persist(new User("michael.lefevre@amdm.fr", "ml", 87));
		userDao.persist(new User("damien.urvoix@amdm.fr", "du"));
		userDao.persist(new User("jean-pierre.cluzel@amdm.fr", "jpc"));
	}
}
