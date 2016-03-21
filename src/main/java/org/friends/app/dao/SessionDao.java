package org.friends.app.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.friends.app.model.Session;

public class SessionDao {
	
	private static List<Session> cache = new ArrayList<>();
	
	public Session findFirst(Predicate<Session> p) {
		Optional<Session> o = cache.stream().filter(p).findFirst();
		return o.isPresent() ? o.get() : null;
	}

	public Session persist(Session session) {
		cache.add(session);
		return session;
	}

	public void deleteExpired() {
		Date now = Calendar.getInstance().getTime();
		for (Iterator<Session> iterator = cache.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (session.getExpirationDate().before(now))
				iterator.remove();
		}
	}
}
