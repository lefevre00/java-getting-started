package org.friends.app;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.friends.app.dao.PlaceDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.view.Application;

public class StartParking {


	public static void main(String[] args) throws SQLException, URISyntaxException {
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
			
			public void start() throws SQLException, URISyntaxException {
				super.start();
				Connection connexion = getConnection();
				Statement stmt = connexion.createStatement();
				stmt.executeUpdate("DROP TABLE users");
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL, email VARCHAR(255) NOT NULL, place_id INT, pwd varchar(255) NOT NULL, token varchar(100), PRIMARY KEY (id))");
				stmt.executeUpdate("DROP TABLE places");
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS places (id int NOT NULL, email_occupant varchar(255), occupation_date varchar(10) NOT NULL, PRIMARY KEY (id, occupation_date))");
				stmt.executeUpdate("DROP TABLE sessions");
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sessions (user_id int NOT NULL, creation_date TIMESTAMP NOT NULL DEFAULT NOW(), cookie character varying(100) NOT NULL, PRIMARY KEY (cookie))");
				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 1, 'william.verdeil@amdm.fr', 141 , 'wv','token' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1 AND email = 'william.verdeil@amdm.fr')");
				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 2, 'abdel.tamditi@amdm.fr', 133 , 'at','token1' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 2 AND email = 'abdel.tamditi@amdm.fr')");
				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 3, 'michael.lefevre@amdm.fr', 87 , 'ml','token2' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 3 AND email = 'michael.lefevre@amdm.fr')");
				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 4, 'damien.urvoix@amdm.fr', null , 'du','token3' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 4 AND email = 'damien.urvoix@amdm.fr')");
				stmt.executeUpdate("INSERT INTO USERS (id, email, place_id, pwd, token) SELECT 5, 'jean-pierre.cluzel@amdm.fr', null , 'jpc','token4' WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 5 AND email = 'jean-pierre.cluzel@amdm.fr')");
				initData();
				stmt.close();
				connexion.close();
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
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
		LocalDate timePoint = LocalDate.now();
		String strDateToday  = timePoint.format(formatter);
		String strTomorrow = timePoint.plusDays(1).format(formatter);
		String strYearsteday = timePoint.minusDays(1).format(formatter);
		PlaceDao placeDao = new PlaceDao();
		
    	placeDao.persist(new Place(1, true, strDateToday));//Place libre aujourd'hui free = true
    	placeDao.persist(new Place(2, false, strDateToday));//Place occupée aujourd'hui
    	placeDao.persist(new Place(3, "damien.urvoix@amdm.fr", strDateToday));//place occupée aujourd'hui
    	placeDao.persist(new Place(4, null, strDateToday)); // place libre  aujourd'hui
    	placeDao.persist(new Place(33, true, strDateToday)); //Place libre aujourd'hui free = true
    	placeDao.persist(new Place(34, true, strYearsteday)); //Place libre hier free = true
    	placeDao.persist(new Place(35, true, strTomorrow)); //Place libre dans le futur free = true
    	placeDao.persist(new Place(36, null, strYearsteday)); //Place libre hier occupee == null
    	placeDao.persist(new Place(37, null, strTomorrow)); //Place libre dans le futur occupee == null
	}
}
