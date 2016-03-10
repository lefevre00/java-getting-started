package org.friends.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.friends.app.dao.PlaceDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Place;
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
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
		LocalDate timePoint = LocalDate.now();
		String strDateToday  = timePoint.format(formatter);
		String strTomorrow = timePoint.plusYears(1).format(formatter);
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
