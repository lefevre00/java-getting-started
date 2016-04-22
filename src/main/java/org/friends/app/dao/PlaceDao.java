package org.friends.app.dao;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.friends.app.HibernateUtil;
import org.friends.app.model.Place;
import org.friends.app.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import spark.utils.Assert;

public class PlaceDao {

	Session sessionHibernate = HibernateUtil.getSession();
	
	private final static Session session =  HibernateUtil.getSession();
	
	public Place persist(Place place) {
		session.beginTransaction();
		Serializable id = session.save( place );
		session.getTransaction().commit();
		return (Place) session.get(Place.class, id);
	}
	
	public void update(Place place){
		session.beginTransaction();
		session.getNamedQuery(Place.QUERY_RESERVE_PLACE)
		.setEntity("id", new Place(place.getPlaceNumber(), place.getOccupationDate()))
		.setString("email", place.getUsedBy())
		.executeUpdate();
		session.getTransaction().commit();
	}

	public void delete(String date, Integer placeNumber){
		Assert.notNull(date);
		Assert.notNull(placeNumber);
		session.beginTransaction();
		Place item = session.get(Place.class, new Place.PlacePK(placeNumber, date));
		if (item != null) {
			session.delete(item);	
		}
		session.getTransaction().commit();
	}

	
	public void clearAllPlacesBeforeDate(LocalDate date) {
		Assert.notNull(date);
		String strDateRecherche = DateUtil.dateToString(date);
		List<Place> listeDesPlacesAsupprimer = findPlacesByCriterions(Restrictions.le("id.occupationDate", strDateRecherche));
		for (Iterator<Place> iterator = listeDesPlacesAsupprimer.iterator(); iterator.hasNext();) {
			Place place = iterator.next();
			session.delete(place);	
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Place> findPlacesByCriterions(Criterion ... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		
		Criteria criteria = HibernateUtil.getSession().createCriteria(Place.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		criteria.addOrder(Order.asc("id.occupationDate"));	
		return (List<Place>) criteria.list();
	}
	
	public Place findPlaceByCriterions(Criterion ... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		
		Criteria criteria = HibernateUtil.getSession().createCriteria(Place.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		return (Place) criteria.uniqueResult();
	}

	
}

