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

	public UserSession findByCookie(String cookie) {
		getSession().beginTransaction();
		UserSession back = (UserSession ) getSession().getNamedQuery(UserSession.QUERY_FIND_BY_COOKIE).setParameter("cookie", cookie).getSingleResult();
		getSession().getTransaction().commit();
		return back;
	}
}
