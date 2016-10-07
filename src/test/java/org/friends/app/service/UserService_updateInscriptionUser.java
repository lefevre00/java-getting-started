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
