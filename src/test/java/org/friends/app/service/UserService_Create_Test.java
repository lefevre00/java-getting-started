package org.friends.app.service;

import org.friends.app.ParkingTest;
import org.friends.app.dao.UserDao;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserService_Create_Test extends ParkingTest {

	@InjectMocks
	UserServiceBean service = new UserServiceBean();

	@Mock
	UserDao dao;
	@Mock
	MailService mailServiceBean;

	/*
	 * Test avec user null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void un_user_ne_doit_pas_etre_null() throws Exception {
		service.create(null, null);
	}

	/*
	 * Test avec email null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void un_user_doit_avoir_un_email() throws Exception {
		User user = UserBuilder.unUser().build(null, "mdp");
		service.create(user, null);
	}

	/*
	 * Test avec mot de passe null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void un_user_doit_avoir_un_mot_de_passe() throws Exception {
		User user = UserBuilder.unUser().build("email@gmail.com", null);
		service.create(user, null);
	}

	/*
	 * Email ok
	 */
	@Test()
	public void un_user_doit_avoir_un_email_valide_et_un_mdp() throws Exception {
		User user = UserBuilder.unUser().build("prenom.nom@amdm.fr", "mdp");
		service.create(user, "http://localhost:8080/");
	}
}
