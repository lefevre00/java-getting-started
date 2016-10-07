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

import java.util.List;

import org.friends.app.model.Place;
import org.friends.app.model.Place.PlacePK;
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
public class PlaceDaoImpl extends AbstractDao {

	public Place persist(Place place) {
		Session session = getSession();
		session.beginTransaction();
		PlacePK id = (PlacePK) session.save(place);
		session.getTransaction().commit();
		return findById(id);
	}

	public Place findById(PlacePK id) {
		Session session = getSession();
		session.beginTransaction();
		Place back = session.get(Place.class, id);
		session.getTransaction().commit();
		return back;
	}

	public void update(Place place) {
		Session session = getSession();
		session.beginTransaction();
		session.getNamedQuery(Place.QUERY_RESERVE_PLACE)
				.setParameter("id", new Place.PlacePK(place.getPlaceNumber(), place.getOccupationDate()))
				.setParameter("email", place.getUsedBy())
				.executeUpdate();
		session.getTransaction().commit();
	}

	public void delete(String date, Integer placeNumber) {
		Assert.notNull(date);
		Assert.notNull(placeNumber);
		Session session = getSession();
		session.beginTransaction();
		Place item = session.get(Place.class, new Place.PlacePK(placeNumber, date));
		if (item != null) {
			session.delete(item);
		}
		session.getTransaction().commit();
	}

	public List<Place> findPlacesByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		getSession().beginTransaction();
		Criteria criteria = getSession().createCriteria(Place.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		criteria.addOrder(Order.asc("id.occupationDate"));
		List back = criteria.list();
		getSession().getTransaction().commit();
		return back;
	}

	public Place findPlaceByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		getSession().beginTransaction();
		Criteria criteria = getSession().createCriteria(Place.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		Place back = (Place) criteria.uniqueResult();
		getSession().getTransaction().commit();
		return back;
	}

}
