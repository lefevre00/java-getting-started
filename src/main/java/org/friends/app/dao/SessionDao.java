package org.friends.app.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.friends.app.HibernateUtil;
import org.friends.app.model.Session;
import org.springframework.stereotype.Repository;

import spark.utils.Assert;

@Repository
public class SessionDao {

	public Session persist(Session session) {
		Assert.notNull(session);
		org.hibernate.Session sessionHibernate = HibernateUtil.getSession();
		sessionHibernate.beginTransaction();
		Serializable id = sessionHibernate.save(session);
		sessionHibernate.getTransaction().commit();
		return sessionHibernate.get(Session.class, id);
	}

	public void deleteExpired() {
		Date now = Calendar.getInstance().getTime();
		org.hibernate.Session sessionHibernate = HibernateUtil.getSession();
		sessionHibernate.beginTransaction();
		sessionHibernate.getNamedQuery(Session.DELETE_EXPIRED).setParameter("date", now).executeUpdate();
		sessionHibernate.getTransaction().commit();
	}

	public Session findByCookie(String cookie) {
		return (Session) HibernateUtil.getSession().getNamedQuery(Session.QUERY_FIND_BY_COOKIE)
				.setParameter("cookie", cookie).getSingleResult();
	}
}
