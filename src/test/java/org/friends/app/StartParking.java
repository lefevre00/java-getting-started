package org.friends.app;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.friends.app.dao.PlaceDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.view.Application;

public class StartParking {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);

	public static void main(String[] args) throws SQLException, URISyntaxException {
		System.setProperty("PORT", "8080");
		System.setProperty(Configuration.DEPLOY_MODE, "dev");
		System.setProperty("MAIL_DEST", "lefevre00@yahoo.fr");

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
			
			public void start() throws SQLException, URISyntaxException {
				super.start();
//				Connection connexion = getConnection();
//				Statement stmt = connexion.createStatement();
//				// A décommenter quand on touche la structure d'une des 3 tables
//				//stmt.executeUpdate("DROP TABLE users");
//				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL, email VARCHAR(255) NOT NULL, place_id INT, pwd varchar(255) NOT NULL, token varchar(100), PRIMARY KEY (id))");
//				//stmt.executeUpdate("DROP TABLE places");
//				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS places (id int NOT NULL, email_occupant varchar(255), occupation_date varchar(10) NOT NULL, PRIMARY KEY (id, occupation_date))");
//				
//				//stmt.executeUpdate("DROP TABLE sessions");
//				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sessions (user_id int NOT NULL, creation_date TIMESTAMP NOT NULL DEFAULT NOW(), cookie character varying(100) NOT NULL, expiration_date timestamp NOT NULL,  PRIMARY KEY (cookie))");
//				// Création des utilisateurs
//				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 1, 'william.verdeil@amdm.fr', 141 , 'wv','token' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1 AND email = 'william.verdeil@amdm.fr')");
//				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 2, 'abdel.tamditi@amdm.fr', 133 , 'at','token1' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 2 AND email = 'abdel.tamditi@amdm.fr')");
//				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 3, 'michael.lefevre@amdm.fr', 87 , 'ml','token2' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 3 AND email = 'michael.lefevre@amdm.fr')");
//				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 4, 'damien.urvoix@amdm.fr', null , 'du','token3' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 4 AND email = 'damien.urvoix@amdm.fr')");
//				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 5, 'jean-pierre.cluzel@amdm.fr', null , 'jpc','token4' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 5 AND email = 'jean-pierre.cluzel@amdm.fr')");
//				
//				LocalDate timePoint = LocalDate.now();
//				String strDateToday  = timePoint.format(formatter);
//				String strTomorrow = timePoint.plusDays(1).format(formatter);
//				String strYearsteday = timePoint.minusDays(1).format(formatter);
//				// Création des places
//				stmt.executeUpdate("DELETE FROM places");
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 1, null, '" + strDateToday + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=1 AND email_occupant = null AND occupation_date = '" + strDateToday + "')");
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 4, null, '" + strDateToday + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=4 AND email_occupant = null AND occupation_date = '" + strDateToday + "')");
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 141, 'damien.urvoix@amdm.fr', '" + strDateToday + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=141 AND email_occupant = 'damien.urvoix@amdm.fr' AND occupation_date = '" + strDateToday + "')");
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 1, null, '" + strTomorrow + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=1 AND email_occupant = null AND occupation_date = '" + strTomorrow + "')");
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 4, null, '" + strTomorrow + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=4 AND email_occupant = null AND occupation_date = '" + strTomorrow + "')");
//				
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 1, null, '" + strYearsteday + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=1 AND email_occupant = null AND occupation_date = '" + strYearsteday + "')");
//				stmt.executeUpdate("INSERT INTO places (id, email_occupant, occupation_date) SELECT 4, null, '" + strYearsteday + "' WHERE NOT EXISTS (SELECT 1 FROM places WHERE id=4 AND email_occupant = null AND occupation_date = '" + strYearsteday + "')");
				
				initData();
//				stmt.close();
//				connexion.close();
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
		
		
		LocalDate timePoint = LocalDate.now();
		String strDateToday  = timePoint.format(formatter);
		String strTomorrow = timePoint.plusDays(1).format(formatter);
		String strYearsteday = timePoint.minusDays(1).format(formatter);
		PlaceDao placeDao = new PlaceDao();
		
    	placeDao.persist(new Place(1, strDateToday)); //Place libre aujourd'hui free = true
    	placeDao.persist(new Place(2, "damien.urvoix@amdm.fr", strDateToday));//place occupée aujourd'hui
    	placeDao.persist(new Place(34, strYearsteday)); //Place libre hier free = true
    	placeDao.persist(new Place(35, strTomorrow)); //Place libre demain
    	placeDao.persist(new Place(36, "damien.urvoix@amdm.fr", strYearsteday)); //Place occupee hier
    	placeDao.persist(new Place(37, strTomorrow)); //Place libre demain
	}
}
