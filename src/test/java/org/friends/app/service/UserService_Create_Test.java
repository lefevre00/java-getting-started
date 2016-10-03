package org.friends.app.service;

import static org.friends.app.service.UserBuilder.unUser;

import org.friends.app.dao.impl.UserDaoImpl;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserService_Create_Test {

	private static final String PRENOM_NOM_AMDM_FR = "prenom.nom@amdm.fr";
	private static final String PRENOM_NOM_NON_AMDM_FR = "prenom.nom@gmail.fr";

	@InjectMocks
	UserServiceBean service = new UserServiceBean();

	@Mock
	UserDaoImpl dao;
	
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
		service.create(unUser().mdp("mdp").build(), null);
	}




	/*
	 * Email ok
	 */
	@Test()
	public void un_user_doit_avoir_un_email_valide_et_un_mdp() throws Exception {
		service.create(unUser().email(PRENOM_NOM_AMDM_FR).mdp("mdp").build(), "http://localhost:9090/");
	}



	/*
	 * Test email non valide
	 */
	@Test(expected = Exception.class)
	public void un_user_doit_avoir_mail_valide() throws Exception {
		service.create(unUser().email(PRENOM_NOM_NON_AMDM_FR).mdp("mdp").build(), "http://localhost:9090/");
	}

}
