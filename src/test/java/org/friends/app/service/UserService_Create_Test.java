package org.friends.app.service;

import org.friends.app.ParkingTest;
import org.friends.app.dao.UserDao;
import org.friends.app.model.User;
import org.friends.app.service.impl.MailServiceBean;
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
	
	@Mock UserDao dao;
	@Mock MailServiceBean mailServiceBean;
	
	/*
	 * Test avec user null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void un_user_ne_doit_pas_etre_null() throws Exception {
		service.create(null, null);
	}
	
	/*
	 * Test avec email null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void un_user_doit_avoir_un_email() throws Exception {
		User user = UserBuilder.unUser().build(null, "mdp");
		service.create(user, null);
	}	
	
	/*
	 * Test avec mot de passe null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void un_user_doit_avoir_un_mot_de_passe() throws Exception {
		User user = UserBuilder.unUser().build("email@gmail.com", null);
		service.create(user, null);
	}

	/*
	 * Test email AMDM sans '.' 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_avec_point() throws Exception {
		User user = UserBuilder.unUser().build("nom@amdm.fr", "mdp");
		service.create(user, null);		
	}
	
	
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_avec_deux_points() throws Exception {
		User user = UserBuilder.unUser().build("prenom.nom.test@amdm.fr", "mdp");
		service.create(user, null);		
	}	
	
	/*
	 * Test email ne terminant pas par '@amdm.fr'
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_amdm() throws Exception {
		User user = UserBuilder.unUser().build("prenom.nom@gmail.com", "mdp");
		service.create(user, null);
	}	
	
	/*
	 * Test email contenant un chiffre
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide() throws Exception {
		User user = UserBuilder.unUser().build("prenom.1nom@amdm.fr", "mdp");
		service.create(user, null);
	}	
	
	/*
	 * Test email 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide_cas_1() throws Exception {
		User user = UserBuilder.unUser().build("prenom.nom@amdmfr", "mdp");
		service.create(user, null);
	}		
	
	/*
	 * Test email 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide_cas_2() throws Exception {
		User user = UserBuilder.unUser().build("prenom.nom@am.dm.fr", "mdp");
		service.create(user, null);
	}		
	
	/*
	 * Test email 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide_cas_3() throws Exception {
		User user = UserBuilder.unUser().build("prenom..nom@amdm.fr", "mdp");
		service.create(user, null);
	}	
	
	/*
	 * Test email 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide_cas_4() throws Exception {
		User user = UserBuilder.unUser().build("pre-nom.nom@am-dm.fr", "mdp");
		service.create(user, null);
	}		
	
	/*
	 * Test email 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide_cas_5() throws Exception {
		User user = UserBuilder.unUser().build("pre--nom.nom@amdm.fr", "mdp");
		service.create(user, null);
	}	
	
	/*
	 * Test email 
	 */
	@Test(expected=Exception.class)
	public void un_user_doit_avoir_un_email_valide_cas_6() throws Exception {
		User user = UserBuilder.unUser().build("prenom.@amdm.fr", "mdp");
		service.create(user, null);
	}		
	
	/*
	 * Email OK
	 */
	@Test()
	public void un_user_doit_avoir_un_email_valide_cas_7() throws Exception {
		User user = UserBuilder.unUser().build("pre-nom.nom@amdm.fr", "mdp");
		service.create(user, null);
	}
	
	/*
	 * Email OK 
	 */
	@Test()
	public void un_user_doit_avoir_un_email_valide_cas_8() throws Exception {
		User user = UserBuilder.unUser().build("prenom.no-m@amdm.fr", "mdp");
		service.create(user, null);
	}		
	
	/*
	 * Email Ok
	 */
	@Test()
	public void un_user_doit_avoir_un_email_valide_cas_9() throws Exception {
		User user = UserBuilder.unUser().build("pre-nom.no-m@amdm.fr", "mdp");
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
