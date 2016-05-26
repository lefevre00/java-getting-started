package org.friends.app.service;

import java.util.ArrayList;

import org.friends.app.ParkingTest;
import org.friends.app.dao.UserDao;
import org.friends.app.dao.UserDaoTest;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.CORBA.UnknownUserException;

@RunWith(MockitoJUnitRunner.class)
public class UserService_Delete_Test extends ParkingTest {

	private static final int ID_USER_SANS_PLACE = 1;
	private static final int ID_USER_AVEC_PLACE = 2;

	@InjectMocks
	UserServiceBean serviceTested = new UserServiceBean();

	@Mock
	UserDao userDao;
	@Mock
	PlaceService placeService;

	/*
	 * Data used in many methods
	 */
	static ArrayList<Place> places = new ArrayList<>();

	private static User userAvecPlace;

	private static User userSansPlace;
	private static Place place;

	@BeforeClass
	public static void setupClass() {
		place = new Place(111, "2050-01-01");
		places.add(place);
		userAvecPlace = UserBuilder.unUser().id(ID_USER_AVEC_PLACE).email(UserDaoTest.EMAIL_ABDEL).mdp("titi")
				.place(111).build();
		userSansPlace = UserBuilder.unUser().id(ID_USER_SANS_PLACE).email(UserDaoTest.EMAIL_ABDEL).mdp("titi").build();
	}

	@After
	public void setupMethod() {
		place.setUsedBy(null);
	}

	/*
	 * Test avec user null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void un_user_ne_doit_pas_etre_null() throws Exception {
		serviceTested.delete(null);
	}

	@Test(expected = UnknownUserException.class)
	public void le_user_doit_exister_en_base() throws Exception {
		serviceTested.delete(new User());
	}

	@Test
	public void cas_user_sans_place_ni_reservation() throws Exception {

		Mockito.when(userDao.findById(ID_USER_SANS_PLACE)).thenReturn(userSansPlace);

		serviceTested.delete(userSansPlace);

		Mockito.verify(userDao).findById(ID_USER_SANS_PLACE);
		Mockito.verify(userDao).delete(ID_USER_SANS_PLACE);
	}

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void cas_user_avec_reservation() throws Exception {
		expected.expect(DataIntegrityException.class);
		expected.expectMessage(UserService.USER_DELETE_BOOK);

		Mockito.when(userDao.findById(ID_USER_SANS_PLACE)).thenReturn(userSansPlace);
		Mockito.when(placeService.getReservations(userSansPlace)).thenReturn(places);

		serviceTested.delete(userSansPlace);
	}

	@Test
	public void cas_user_avec_place_sans_partage() throws Exception {

		Mockito.when(userDao.findById(ID_USER_AVEC_PLACE)).thenReturn(userAvecPlace);
		Mockito.when(placeService.getShared(userAvecPlace)).thenReturn(new ArrayList<>());

		serviceTested.delete(userAvecPlace);

		Mockito.verify(userDao).findById(ID_USER_AVEC_PLACE);
		Mockito.verify(userDao).delete(ID_USER_AVEC_PLACE);
	}

	@Test
	public void cas_user_avec_place_avec_partage_libre() throws Exception {
		expected.expect(DataIntegrityException.class);
		expected.expectMessage(UserService.USER_DELETE_SHARE);

		Mockito.when(userDao.findById(ID_USER_AVEC_PLACE)).thenReturn(userAvecPlace);
		Mockito.when(placeService.getShared(userAvecPlace)).thenReturn(places);

		serviceTested.delete(userAvecPlace);
	}

	@Test
	public void cas_user_avec_place_avec_partage_occupe() throws Exception {
		expected.expect(DataIntegrityException.class);
		expected.expectMessage(UserService.USER_DELETE_USED);

		place.setUsedBy(UserDaoTest.EMAIL_ABDEL);

		Mockito.when(userDao.findById(ID_USER_AVEC_PLACE)).thenReturn(userAvecPlace);
		Mockito.when(placeService.getShared(userAvecPlace)).thenReturn(places);

		serviceTested.delete(userAvecPlace);

		Assert.fail("TODO MILEF");
	}
}