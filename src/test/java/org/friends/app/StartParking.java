package org.friends.app;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDate;

import org.friends.app.dao.PlaceDao;
import org.friends.app.dao.SessionDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Application;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class StartParking {

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
		userDao.persist(new User("abdel.tamditi@amdm.fr", md5("at"), 133));
		userDao.persist(new User("william.verdeil@amdm.fr", md5("wv"), 141));
		userDao.persist(new User("michael.lefevre@amdm.fr", md5("ml"), 87));
		userDao.persist(new User("damien.urvoix@amdm.fr", md5("du")));
		userDao.persist(new User("jean-pierre.cluzel@amdm.fr", md5("jpc")));
		
		LocalDate timePoint = LocalDate.now();
		String strDateToday  = DateUtil.dateToString(timePoint);
		String strTomorrow = DateUtil.dateToString(timePoint.plusDays(1));
		String strYearsteday = DateUtil.dateToString(timePoint.minusDays(1));
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

	private static String md5(String pwd) {
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher().putString(pwd, Charset.defaultCharset()).hash();
		return hc.toString();
	}
}
