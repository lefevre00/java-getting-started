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
