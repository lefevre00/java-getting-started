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
