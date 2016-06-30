package org.friends.app.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.friends.app.HibernateUtil;
import org.friends.app.ParkingTest;
import org.friends.app.model.Place;
import org.friends.app.util.DateUtil;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlaceDaoTest extends ParkingTest {

	private static String MAIL_RESERVANT = "damien.urvoix@amdm.fr";
	static LocalDate timePoint = DateUtil.now();
	static String strDateToday = DateUtil.dateToString(timePoint);
	static String strTomorrow = DateUtil.dateToString(timePoint.plusDays(1));
	static String strApresDemain = DateUtil.dateToString(timePoint.plusDays(2));
	static String strYearsteday = DateUtil.dateToString(timePoint.minusDays(1));

	private PlaceDao placeDao;

	@BeforeClass
	public static void beforeClass() {
		HibernateUtil.getSession();
	}

	@AfterClass
	public static void afterClass() {
		HibernateUtil.closeSession();
	}

	@After
	public void after() {
		List<Place> l = placeDao.findPlacesByCriterions(Restrictions.isNotNull("id.occupationDate"));
		l.stream().forEach(p -> {
			placeDao.delete(p.getOccupationDate(), p.getPlaceNumber());
		});

		Transaction t = HibernateUtil.getSession().getTransaction();
		if (t.isActive())
			t.rollback();
	}

	@Before
	public void beforeTestMethod() {
		placeDao = new PlaceDao();
		placeDao.persist(new Place(new Integer(3), MAIL_RESERVANT, strTomorrow));
		placeDao.persist(new Place(new Integer(1), strDateToday)); // Place
																	// libre
																	// aujourd'hui
																	// free =
																	// true
		placeDao.persist(new Place(new Integer(141), strDateToday));
		placeDao.persist(new Place(new Integer(2), MAIL_RESERVANT, strDateToday));// place
																					// occupée
																					// aujourd'hui
		placeDao.persist(new Place(new Integer(35), MAIL_RESERVANT, strApresDemain));// place
																						// occupée
																						// aujourd'hui
		placeDao.persist(new Place(new Integer(34), strYearsteday)); // Place
																		// libre
																		// hier
																		// free
																		// =
																		// true
		placeDao.persist(new Place(new Integer(38), strTomorrow)); // Place
																	// libre
																	// demain
		placeDao.persist(new Place(new Integer(35), strTomorrow)); // Place
																	// libre
																	// demain
		placeDao.persist(new Place(new Integer(36), MAIL_RESERVANT, strYearsteday)); // Place
																						// occupee
																						// hier
		placeDao.persist(new Place(new Integer(37), strTomorrow)); // Place
																	// libre
																	// demain
	}

	@Test
	public void findAllFreeByDate_avec_date() {
		List<Place> lesPlacesLibresAujourdhui = placeDao.findPlacesByCriterions(
				Restrictions.eq("id.occupationDate", strDateToday), Restrictions.isNull("usedBy"));
		Assert.assertEquals("On attend 2 places libres aujourd'hui", 2, lesPlacesLibresAujourdhui.size());
	}

	// @Test(expected=IllegalArgumentException.class)
	// public void findReleaseHistoryByPlace_sans_param() {
	// placeDao.findReleaseHistoryByPlace(null);
	// }

	@Test
	public void findReleaseHistoryByPlace() {
		List<Place> historiquePlace35 = placeDao
				.findPlacesByCriterions(Restrictions.eq("id.placeNumber", new Integer(35)));
		Assert.assertEquals("On attend n places", 2, historiquePlace35.size());
	}

	@Test
	public void UserAPasDejaUnePlaceReserveDans10jours() {
		String dans10jours = DateUtil.dateToString(timePoint.plusDays(10));
		List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(
				Restrictions.eq("id.occupationDate", dans10jours), Restrictions.eq("usedBy", MAIL_RESERVANT));
		Assert.assertEquals(MAIL_RESERVANT + " n'a pas réservé une place dans 4 jours", false,
				(listPlaceReserve != null && listPlaceReserve.size() > 0) ? true : false);
	}

	@Test
	public void unePlaceEstLibreAujourdhui() {
		List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(
				Restrictions.eq("id.occupationDate", strDateToday), Restrictions.eq("id.placeNumber", new Integer(1)));
		Assert.assertEquals("La place 1 est libre aujourd'hui", true,
				(listPlaceReserve != null && listPlaceReserve.size() > 0) ? true : false);
	}

	@Test
	public void la_place_200_ne_doit_pas_etre_libre_aujourdhui() {
		List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(
				Restrictions.eq("id.occupationDate", strDateToday), Restrictions.eq("id.placeNumber", new Integer(200)),
				Restrictions.isNull("usedBy"));
		Assert.assertEquals("La place 200 n'est libre aujourd'hui", false,
				(listPlaceReserve != null && listPlaceReserve.size() > 0) ? true : false);
	}

	@Test
	public void changementHeure() {
		int nbPlace = 2;
		int idPremierePlace = 3;
		String dateReservation = strTomorrow;
		if (LocalDateTime.now().getHour() <= Place.HEURE_CHANGEMENT_JOUR_RECHERCHE) {
			nbPlace = 3;
			idPremierePlace = 2;
			dateReservation = strDateToday;
		}
		List<Place> lesPlacesReserveesParDamien = placeDao.findPlacesByCriterions(
				Restrictions.eq("usedBy", MAIL_RESERVANT), Restrictions.ge("id.occupationDate", dateReservation));
		Assert.assertEquals("On attend n places", nbPlace, lesPlacesReserveesParDamien.size());
		Place premier = lesPlacesReserveesParDamien.get(0);
		Assert.assertEquals("Damien a réservé la place " + premier.getPlaceNumber().intValue(), idPremierePlace,
				premier.getPlaceNumber().intValue());
		Assert.assertEquals(
				"Damien a réservé la place " + premier.getPlaceNumber().intValue() + " le " + dateReservation,
				dateReservation, premier.getOccupationDate());
	}

	@Test
	public void statistics() {
		List<Place> listAllPlace = placeDao.findPlacesByCriterions(Restrictions.ge("id.occupationDate", strYearsteday),
				Restrictions.le("id.occupationDate", strApresDemain));
		Assert.assertEquals("10 places ont été libérées en le " + strYearsteday + " et le " + strApresDemain, 10,
				listAllPlace.size());
	}
}
