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

import org.friends.app.ClientTest;
import org.friends.app.dao.impl.UserDaoImpl;
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
public class UserService_Update_Test extends ClientTest {
	
	@InjectMocks
	UserServiceBean service = new UserServiceBean();
	
	@Mock UserDaoImpl dao;
	
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
