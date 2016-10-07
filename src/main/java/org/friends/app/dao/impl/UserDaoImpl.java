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
import java.util.List;

import org.friends.app.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spark.utils.Assert;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class UserDaoImpl extends AbstractDao {

	public User persist(User user) {
		Assert.notNull(user);
		Session session = getSession();
		session.beginTransaction();
		Serializable id = null;
		if (user.getId() != null) {
			id = user.getId();
			session.merge(user);
		} else {
			id = session.save(user);
		}
		session.getTransaction().commit();
		return findById((Integer) id);
	}

	public User findById(Integer userId) {
		Session session = getSession();
		session.beginTransaction();
		User back = session.get(User.class, userId);
		session.getTransaction().commit();
		return back;
	}

	public User findUserByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		getSession().beginTransaction();
		Criteria criteria = getSession().createCriteria(User.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		User back = (User) criteria.uniqueResult();
		getSession().getTransaction().commit();
		return back;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUserByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		getSession().beginTransaction();
		Criteria criteria = getSession().createCriteria(User.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		criteria.addOrder(Order.asc("emailAMDM"));
		List back = criteria.list();
		getSession().getTransaction().commit();
		return back;
	}

	public void delete(Integer id) {
		User user = findById(id);
		Session session = getSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
	}

	public void update(User user) {
		Session session = getSession();
		session.beginTransaction();
		session.getNamedQuery(User.QUERY_UPDATE_USER).setParameter("id", user.getId())
				.setParameter("email", user.getEmailAMDM()).setParameter("placeNumber", user.getPlaceNumber())
				.executeUpdate();
		session.getTransaction().commit();
	}
}
