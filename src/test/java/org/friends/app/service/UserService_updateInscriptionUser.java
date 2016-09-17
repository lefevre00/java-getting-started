package org.friends.app.service;

import org.friends.app.ClientTest;
import org.friends.app.dao.impl.UserDaoImpl;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserService_updateInscriptionUser extends ClientTest {
	
	@InjectMocks
	UserServiceBean service = new UserServiceBean();
	
	@Mock UserDaoImpl dao;
	
	@Test(expected=IllegalArgumentException.class)
	public void user_ne_doit_pas_etre_null() throws Exception {
		service.updateInscriptionUser(null, null, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void le_mot_de_passe_ne_doit_pas_etre_null() throws Exception {
		service.updateInscriptionUser(new User(), null,null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void l_url_serveur_ne_doit_pas_etre_nulle() throws Exception {
		service.updateInscriptionUser(new User(), "nn", null);
	}

}
