package org.friends.app;

import java.nio.charset.Charset;
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

	public static void main(String[] args) {
		System.setProperty("PORT", "8080");
		System.setProperty(Configuration.DEPLOY_MODE, "dev");
		System.setProperty("MAIL_TEAM", "contact@takemyplace.fr");

		new Application() {
			@Override
			public void start() {
				super.start();
				initData();
			}
		}.start();
	}

	private static void initData() {
		UserDao userDao = new UserDao();
		userDao.persist(new User("abdel.tamditi@amdm.fr", md5("at"), 133));
		userDao.persist(new User("william.verdeil@amdm.fr", md5("wv"), 141));
		userDao.persist(new User("michael.lefevre@amdm.fr", md5("ml"), 87));
		userDao.persist(new User("damien.urvoix@amdm.fr", md5("du")));
		userDao.persist(new User("jean-pierre.cluzel@amdm.fr", md5("jpc")));
		userDao.persist(new User("marc.coniglio@amdm.fr", md5("jpc"), 33));
		userDao.persist(new User("jean-yves.sironneau@amdm.fr", md5("jpc")));
		userDao.persist(new User("philippe.martins@amdm.fr", md5("jpc"), 94));
		userDao.persist(new User("thibaut.bourelly@amdm.fr", md5("jpc"), 14));
		userDao.persist(new User("dominique.gay@amdm.fr", md5("jpc"), 66));
		userDao.persist(new User("vincent.mathon@amdm.fr", md5("jpc"), 99));
		userDao.persist(new User("sylvain.verneau@amdm.fr", md5("jpc"), 144));

		LocalDate timePoint = DateUtil.now();
		String strDateToday = DateUtil.dateToString(timePoint);
		String strTomorrow = DateUtil.dateToString(timePoint.plusDays(1));
		String strYearsteday = DateUtil.dateToString(timePoint.minusDays(1));
		PlaceDao placeDao = new PlaceDao();

		placeDao.persist(new Place(1, strDateToday)); // Place libre aujourd'hui
														// free = true
		placeDao.persist(new Place(2, "damien.urvoix@amdm.fr", strDateToday));// place
																				// occupée
																				// aujourd'hui
		placeDao.persist(new Place(34, strYearsteday)); // Place libre hier free
														// = true
		placeDao.persist(new Place(35, strTomorrow)); // Place libre demain
		placeDao.persist(new Place(36, "damien.urvoix@amdm.fr", strYearsteday)); // Place
																					// occupee
																					// hier
		placeDao.persist(new Place(37, strTomorrow)); // Place libre demain

		insertDatas(strYearsteday, strDateToday, strTomorrow);
		
		SessionDao sessionDao = new SessionDao();
		sessionDao.deleteExpired();
	}

	private static String md5(String pwd) {
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher().putString(pwd, Charset.defaultCharset()).hash();
		return hc.toString();
	}
	
	
	
	private static void insertDatas(String yesterday, String today, String tomorrow){
		PlaceDao placeDao = new PlaceDao();

		placeDao.persist(new Place(133, "damien.urvoix@amdm.fr",yesterday));
		placeDao.persist(new Place(132, "jean-pierre@amdm.fr",yesterday));
		placeDao.persist(new Place(131, "philippe@amdm.fr",yesterday));
		placeDao.persist(new Place(130, yesterday));
														
		placeDao.persist(new Place(80, today));
		placeDao.persist(new Place(81, today));
		placeDao.persist(new Place(82, today));
		placeDao.persist(new Place(133, "damien.urvoix@amdm.fr", today));
		
		placeDao.persist(new Place(120, "damien.urvoix@amdm.fr", tomorrow));
		placeDao.persist(new Place(121, tomorrow));
		placeDao.persist(new Place(122, "philippe.martins@amdm.fr", tomorrow));
		placeDao.persist(new Place(133, tomorrow));
	}
}
