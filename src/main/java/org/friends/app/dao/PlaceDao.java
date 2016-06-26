package org.friends.app.dao;

import java.io.Serializable;
import java.util.List;

import org.friends.app.HibernateUtil;
import org.friends.app.model.Place;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import spark.utils.Assert;

@Repository
public class PlaceDao {

	Session sessionHibernate = HibernateUtil.getSession();

	private final static Session session = HibernateUtil.getSession();

	public Place persist(Place place) {
		session.beginTransaction();
		Serializable id = session.save(place);
		session.getTransaction().commit();
		return session.get(Place.class, id);
	}

	public void update(Place place) {
		session.beginTransaction();
		session.getNamedQuery(Place.QUERY_RESERVE_PLACE)
				.setParameter("id", new Place(place.getPlaceNumber(), place.getOccupationDate()))
				.setParameter("email", place.getUsedBy()).executeUpdate();
		session.getTransaction().commit();
	}

	public void delete(String date, Integer placeNumber) {
		Assert.notNull(date);
		Assert.notNull(placeNumber);
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

		Criteria criteria = HibernateUtil.getSession().createCriteria(Place.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		criteria.addOrder(Order.asc("id.occupationDate"));
		return criteria.list();
	}

	public Place findPlaceByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");

		Criteria criteria = HibernateUtil.getSession().createCriteria(Place.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		return (Place) criteria.uniqueResult();
	}

}
