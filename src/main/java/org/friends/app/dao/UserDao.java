package org.friends.app.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.friends.app.HibernateUtil;
import org.friends.app.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import spark.utils.Assert;

@Repository
public class UserDao {

	public User persist(User user) {
		Assert.notNull(user);
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Serializable id = null;
		if (user.getId() != null) {
			id = user.getId();
			session.merge(user);
		} else {
			id = session.save(user);
		}
		session.getTransaction().commit();
		User back = session.get(User.class, id);
		return back;
	}

	public User findById(Integer userId) {
		return HibernateUtil.getSession().get(User.class, userId);
	}

	public User findUserByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");

		Criteria criteria = HibernateUtil.getSession().createCriteria(User.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}

		return (User) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUserByCriterions(Criterion... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");

		Criteria criteria = HibernateUtil.getSession().createCriteria(User.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		criteria.addOrder(Order.asc("emailAMDM"));

		return criteria.list();
	}

	public void clearAllUsers() {
		List<User> listeDesUsersAsupprimer = findAllUserByCriterions(Restrictions.isNotNull("id"));
		for (Iterator<User> iterator = listeDesUsersAsupprimer.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			HibernateUtil.getSession().delete(user);
		}
	}

	public void delete(Integer id) {
		User user = findById(id);
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
	}

	public void update(User user) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.getNamedQuery(User.QUERY_UPDATE_USER)
				.setParameter("id", user.getId())
				.setParameter("email", user.getEmailAMDM())
				.setParameter("placeNumber", user.getPlaceNumber()).executeUpdate();
		session.getTransaction().commit();
	}

}
