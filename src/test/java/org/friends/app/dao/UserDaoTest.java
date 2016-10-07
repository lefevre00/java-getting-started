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
package org.friends.app.dao;

import org.friends.app.dao.impl.UserDaoImpl;
import org.friends.app.model.User;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends DbTest {

	public static final String EMAIL_ABDEL = "abdel.tamditi@amdm.fr";
	private static String MAIL_RESERVANT = "damien.urvoix@amdm.fr";

	@Autowired
	private UserDaoImpl userDao;

	@Before
	public void beforeTestMethod() {
		userDao.persist(new User(EMAIL_ABDEL, "at", 133));
		userDao.persist(new User("william.verdeil@amdm.fr", "wv", 141));
		User mick = new User("michael.lefevre@amdm.fr", "ml", 87);
		mick.setTokenMail("mick");
		userDao.persist(mick);
		userDao.persist(new User("damien.urvoix@amdm.fr", "du"));
		User jpc = new User("jean-pierre.cluzel@amdm.fr", "jpc");
		jpc.setTokenPwd("passjpc");
		userDao.persist(jpc);
	}

	@After
	public void clearUsersTable() {
		runSql("delete from Users");
	}

	@Test
	public void findByEmail() {

		User damien = userDao.findUserByCriterions(Restrictions.eq("emailAMDM", MAIL_RESERVANT));
		Assert.assertNotNull("Damien devrait être trouvé", damien);
	}

	@Test
	public void findByEmailNonTrouve() {

		User gerard = userDao.findUserByCriterions(Restrictions.eq("emailAMDM", "gerard.mambu@amdm.fr"));
		Assert.assertNull("Damien devrait être trouvé", gerard);
	}

	@Test
	public void findByTokenMail() {

		User mick = userDao.findUserByCriterions(Restrictions.eq("tokenMail", "mick"));
		Assert.assertNotNull("Le token Mail Mick est ok", mick);
		Assert.assertEquals("Mick a la place 87", Integer.valueOf(87), mick.getPlaceNumber());
		Assert.assertEquals("Mick a le mot de passe : ", "ml", mick.getPwd());
	}

	@Test
	public void findByJPC() {
		User jp = userDao.findUserByCriterions(Restrictions.eq("emailAMDM", "jean-pierre.cluzel@amdm.fr"));
		Assert.assertNotNull("Jp  devrait être trouvé", jp);
		Assert.assertEquals("Le token Password de jpc doit être correct", "passjpc", jp.getTokenPwd());
	}
}
