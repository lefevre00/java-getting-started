package org.friends.app;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.friends.app.dao.PlaceDao;
import org.friends.app.dao.SessionDao;
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
			public void start() throws SQLException, URISyntaxException {
				super.start();
				initData();
			}
		}.start();
	}

	private static void initData() throws SQLException, URISyntaxException {
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

		SessionDao sessionDao = new SessionDao();
		sessionDao.deleteExpired();
	}
}
