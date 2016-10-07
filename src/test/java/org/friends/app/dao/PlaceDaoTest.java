/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.friends.app.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.friends.app.dao.impl.PlaceDaoImpl;
import org.friends.app.model.Place;
import org.friends.app.util.DateUtil;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlaceDaoTest extends DbTest {

	private static String MAIL_RESERVANT = "damien.urvoix@amdm.fr";

	@Autowired
	PlaceDaoImpl placeDao;

	@After
	public void after() {
		runSql("delete from Places");
	}

	@Before
	public void beforeTestMethod() {
		placeDao.persist(new Place(new Integer(3), strTomorrow, MAIL_RESERVANT));
		placeDao.persist(new Place(new Integer(1), strToday)); // Place
																// libre
																// aujourd'hui
																// free =
																// true
		placeDao.persist(new Place(new Integer(141), strToday));
		placeDao.persist(new Place(new Integer(2), strToday, MAIL_RESERVANT));// place
																				// occupée
																				// aujourd'hui
		placeDao.persist(new Place(new Integer(35), strAfterTomorrow, MAIL_RESERVANT));// place
																						// occupée
																						// aujourd'hui
		placeDao.persist(new Place(new Integer(34), strYesterday)); // Place
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
		placeDao.persist(new Place(new Integer(36), strYesterday, MAIL_RESERVANT)); // Place
																					// occupee
																					// hier
		placeDao.persist(new Place(new Integer(37), strTomorrow)); // Place
																	// libre
																	// demain
	}

	@Test
	public void findAllFreeByDate_avec_date() {
		List<Place> lesPlacesLibresAujourdhui = placeDao
				.findPlacesByCriterions(Restrictions.eq("id.occupationDate", strToday), Restrictions.isNull("usedBy"));
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
		List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", strToday),
				Restrictions.eq("id.placeNumber", new Integer(1)));
		Assert.assertEquals("La place 1 est libre aujourd'hui", true,
				(listPlaceReserve != null && listPlaceReserve.size() > 0) ? true : false);
	}

	@Test
	public void la_place_200_ne_doit_pas_etre_libre_aujourdhui() {
		List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", strToday),
				Restrictions.eq("id.placeNumber", new Integer(200)), Restrictions.isNull("usedBy"));
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
			dateReservation = strToday;
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
		List<Place> listAllPlace = placeDao.findPlacesByCriterions(Restrictions.ge("id.occupationDate", strYesterday),
				Restrictions.le("id.occupationDate", strAfterTomorrow));
		Assert.assertEquals("10 places ont été libérées en le " + strYesterday + " et le " + strAfterTomorrow, 10,
				listAllPlace.size());
	}
}
