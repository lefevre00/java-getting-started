package org.friends.app.service;

import static org.friends.app.service.UserBuilder.unUser;

import org.friends.app.ClientTest;
import org.friends.app.dao.impl.UserDaoImpl;
import org.friends.app.service.impl.UserServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserService_updateInscriptionUser extends ClientTest {

	private static final String PRENOM_NOM_AMDM_FR = "prenom.nom@amdm.fr";
	private static final String PRENOM_NOM_NON_AMDM_FR = "prenom.nom@gmail.com";
	
	
	@InjectMocks
	UserServiceBean service = new UserServiceBean();
	
	@Mock UserDaoImpl dao;
	
	@Mock
	MailService mailServiceBean;
	
	@Test(expected=IllegalArgumentException.class)
	public void user_ne_doit_pas_etre_null() throws Exception {
		service.updateInscriptionUser(null, null, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void le_mot_de_passe_ne_doit_pas_etre_null() throws Exception {
		service.updateInscriptionUser(unUser().mdp("mdp").build(), null,null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void l_url_serveur_ne_doit_pas_etre_nulle() throws Exception {
		service.updateInscriptionUser(unUser().mdp("mdp").build(), "nn", null);
	}
	
	@Test(expected=Exception.class)
	public void la_creation_user_ko_mail_non_amdm() throws Exception {
		service.updateInscriptionUser(unUser().mdp("mdp").email(PRENOM_NOM_NON_AMDM_FR).build(), "nn", "http://localhost:9090/");
	}
	
	@Test()
	public void la_creation_user_ok() throws Exception {
		service.updateInscriptionUser(unUser().mdp("mdp").email(PRENOM_NOM_AMDM_FR).build(), "nn", "http://localhost:9090/");
	}

}
