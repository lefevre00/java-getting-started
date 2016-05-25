package org.friends.app.service;

import org.friends.app.ParkingTest;
import org.friends.app.dao.PlaceDao;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaceService_Release_Test extends ParkingTest {
	
	static String uneDateAuBonFormat = "2006-03-01";
	static String uneDateAuMauvaisFormat = "pasbien";
	
	@InjectMocks
	PlaceServiceBean service = new PlaceServiceBean();
	
	@Mock PlaceDao dao;
	
	/*
	 * Test avec user null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void un_user_ne_doit_pas_etre_nulle() throws BookingException {
		service.release(null,null);
	}
	
	/*
	 * Test avec date nulle
	 */
	@Test(expected=IllegalArgumentException.class)
	public void la_date_ne_doit_pas_etre_nulle() throws BookingException {
		service.release(new User(), null);
	}

	/*
	 * Test avec date vide
	 */
	@Test(expected=IllegalArgumentException.class)
	public void la_date_ne_doit_pas_etre_vide() throws BookingException {
		service.release(new User(), "");
	}
}
