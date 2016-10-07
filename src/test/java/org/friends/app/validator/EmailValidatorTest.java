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
package org.friends.app.validator;

import org.junit.Assert;
import org.junit.Test;

public class EmailValidatorTest {

	/*
	 * Test email AMDM sans '.'
	 */
	@Test
	public void un_email_doit_avoir_un_point() {
		assertFalse("nom@amdm.fr");
	}

	private void assertFalse(String email) {
		Assert.assertFalse(EmailValidator.isValid(email));
	}

	private void assertTrue(String email) {
		Assert.assertTrue(EmailValidator.isValid(email));
	}

	@Test
	public void le_destinataire_doit_etre_correct() throws Exception {
		assertFalse("prenom.nom.test@amdm.fr");
		assertFalse("prenom..nom@amdm.fr");
		assertFalse("pre--nom.nom@amdm.fr");
		assertFalse("prenom.@amdm.fr");
	}

	/*
	 * Test email ne terminant pas par '@amdm.fr'
	 */
	@Test
	public void nom_domaine() throws Exception {
		assertFalse("prenom.nom@gmail.com");
		assertFalse("prenom.nom@amdmfr");
		assertFalse("prenom.nom@am.dm.fr");
		assertFalse("pre-nom.nom@am-dm.fr");
	}

	/*
	 * Test email contenant un chiffre
	 */
	@Test
	public void un_email_ne_doit_pas_contenir_de_chiffre() throws Exception {
		assertFalse("prenom.1nom@amdm.fr");
	}

	@Test()
	public void prenom_compose() throws Exception {
		assertTrue("pre-nom.nom@amdm.fr");
	}

	@Test()
	public void nom_compose() throws Exception {
		assertTrue("prenom.no-m@amdm.fr");
	}

	@Test()
	public void prenom_et_nom_composes() throws Exception {
		assertTrue("pre-nom.no-m@amdm.fr");
	}
}
