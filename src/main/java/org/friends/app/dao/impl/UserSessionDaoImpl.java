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
package org.friends.app.dao.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.friends.app.model.UserSession;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spark.utils.Assert;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class UserSessionDaoImpl extends AbstractDao {

	public UserSession persist(UserSession session) {
		Assert.notNull(session);
		getSession().beginTransaction();
		Serializable id = getSession().save(session);
		getSession().getTransaction().commit();
		return findById(id);
	}

	private UserSession findById(Serializable id) {
		getSession().beginTransaction();
		UserSession back = getSession().get(UserSession.class, id);
		getSession().getTransaction().commit();
		return back;
	}

	public void deleteExpired() {
		Date now = Calendar.getInstance().getTime();
		Session session = getSession();
		session.beginTransaction();
		session.getNamedQuery(UserSession.DELETE_EXPIRED).setParameter("date", now).executeUpdate();
		session.getTransaction().commit();
	}
	
	public void deleteUserSessionByUserId(Integer user_id) {
		Session session = getSession();
		session.beginTransaction();
		session.getNamedQuery(UserSession.DELETE_BY_USER_ID).setParameter("user_id", user_id).executeUpdate();
		session.getTransaction().commit();
	}

	public UserSession findByCookie(String cookie) {
		getSession().beginTransaction();
		UserSession back = (UserSession ) getSession().getNamedQuery(UserSession.QUERY_FIND_BY_COOKIE).setParameter("cookie", cookie).getSingleResult();
		getSession().getTransaction().commit();
		return back;
	}
}
