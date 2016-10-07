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
import org.friends.app.dao.impl.PlaceDaoImpl;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaceService_Release_Test extends ClientTest {
	
	static String uneDateAuBonFormat = "2006-03-01";
	static String uneDateAuMauvaisFormat = "pasbien";
	
	@InjectMocks
	PlaceServiceBean service = new PlaceServiceBean();
	
	@Mock PlaceDaoImpl dao;
	
	/*
	 * Test avec user null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void un_user_ne_doit_pas_etre_nulle() throws BookingException {
		service.release(null,null);
	}
	
	/*
	 * Test avec date nulle
	 */
	@Test(expected=IllegalArgumentException.class)
	public void la_date_ne_doit_pas_etre_nulle() throws BookingException {
		service.release(new User(), null);
	}

	/*
	 * Test avec date vide
	 */
	@Test(expected=IllegalArgumentException.class)
	public void la_date_ne_doit_pas_etre_vide() throws BookingException {
		service.release(new User(), "");
	}
}
