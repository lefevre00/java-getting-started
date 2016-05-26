package org.friends.app.service;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.friends.app.ParkingTest;
import org.friends.app.dao.UserDao;
import org.friends.app.dao.UserDaoTest;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.CORBA.UnknownUserException;

@RunWith(MockitoJUnitRunner.class)
public class UserService_Delete_Test extends ParkingTest {

	@InjectMocks
	UserServiceBean service = new UserServiceBean();

	@Mock
	UserDao dao;
	@Mock
	PlaceService placeService;

	/*
	 * Test avec user null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void un_user_ne_doit_pas_etre_null() throws Exception {
		service.delete(null);
	}

	@Test(expected = UnknownUserException.class)
	public void le_user_doit_exister_en_base() throws Exception {
		service.delete(new User());
	}

	@Test
	public void si_user_sans_place_alors_doit_appeler_le_dao() throws Exception {
		User unUser = new User(UserDaoTest.EMAIL_ABDEL, "titi");
		Field field = User.class.getDeclaredField("id");
		field.setAccessible(true);
		field.set(unUser, 1);
		Mockito.when(dao.findById(1)).thenReturn(unUser);
		service.delete(unUser);
		Mockito.verify(dao).findById(1);
		Mockito.verify(dao).delete(1);
	}

	@Test(expected = DataIntegrityException.class)
	public void si_user_avec_place_alors_doit_appeler_le_dao() throws Exception {
		User unUser = new User(UserDaoTest.EMAIL_ABDEL, "titi", 111);
		Field field = User.class.getDeclaredField("id");
		field.setAccessible(true);
		field.set(unUser, 1);
		Mockito.when(dao.findById(1)).thenReturn(unUser);
		ArrayList<Place> places = new ArrayList<>();
		places.add(new Place(111, "2050-01-01"));
		Mockito.when(placeService.getShared(unUser)).thenReturn(places);
		service.delete(unUser);
	}
}