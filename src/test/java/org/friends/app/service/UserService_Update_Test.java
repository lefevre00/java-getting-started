package org.friends.app.service;

import org.friends.app.ParkingTest;
import org.friends.app.dao.UserDao;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserService_Update_Test extends ParkingTest {
	
	@InjectMocks
	UserServiceBean service = new UserServiceBean();
	
	@Mock UserDao dao;
	
	@Test(expected=IllegalArgumentException.class)
	public void user_ne_doit_pas_etre_null() throws Exception {
		service.changePlace(null, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void la_place_ne_doit_pas_etre_null() throws Exception {
		service.changePlace(new User(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void user_doit_avoir_un_id() throws Exception {
		service.changePlace(new User(), 1);
	}

	@Rule
	public ExpectedException expected = ExpectedException.none();

	
	@Test
	public void la_place_ne_doit_pas_etre_deja_attribuee() throws Exception {
		expected.expect(DataIntegrityException.class);
		expected.expectMessage(UserService.PLACE_ALREADY_USED);
		
		int placeEnConflit = 10;
		User userSansPlace = new User("sansPlace", "mdp");
		userSansPlace.setId(1);
		User userAvecPlace = new User("sansPlace", "mdp", placeEnConflit);
		
		Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(userSansPlace);
		Mockito.when(dao.findUserByCriterions(Mockito.any())).thenReturn(userAvecPlace);
		
		User userSansPlaceAutreInstance = new User("sansPlace", "mdp");
		userSansPlaceAutreInstance.setId(1);
		service.changePlace(userSansPlaceAutreInstance, placeEnConflit);
	}
}
