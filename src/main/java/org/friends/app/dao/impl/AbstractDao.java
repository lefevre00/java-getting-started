package org.friends.app.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Session getSession() {
		Session s = sessionFactory.getCurrentSession();
		if (!s.isOpen())
			s = sessionFactory.openSession();
		return s;
	}
}
