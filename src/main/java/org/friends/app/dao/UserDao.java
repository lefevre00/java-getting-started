package org.friends.app.dao;


import java.io.Serializable;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.friends.app.HibernateUtil;
import org.friends.app.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import spark.utils.Assert;

public class UserDao {

	public User persist(User user) throws SQLException, URISyntaxException {
		Assert.notNull(user);
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Serializable id = session.save( user );
		session.getTransaction().commit();
		return (User) session.get(User.class, id);
	}

	public User findById(Integer userId) {
		return (User) HibernateUtil.getSession().get(User.class,userId);
	}

	public User findByTokenMail(String token) {
		Assert.notNull(token);
		return (User) HibernateUtil.getSession().getNamedQuery(User.QUERY_FIND_USER_BY_TOKEN_MAIL)
				.setString("token", token)
				.uniqueResult();
	}

	public User findUserByCriterions(Criterion ... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		
		Criteria criteria = HibernateUtil.getSession().createCriteria(User.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		
		return (User) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUserByCriterions(Criterion ... criterions) {
		Assert.notNull(criterions);
		Assert.notEmpty(criterions, "restrictions must not be empty");
		
		Criteria criteria = HibernateUtil.getSession().createCriteria(User.class);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		
		return (List<User>) criteria.list();
	}

	public void clearAllUsers() {
		// TODO Auto-generated method stub
		List<User> listeDesUsersAsupprimer = findAllUserByCriterions(Restrictions.isNotNull("id"));
		for (Iterator<User> iterator = listeDesUsersAsupprimer.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			HibernateUtil.getSession().delete(user);	
		}
		
	}

}
