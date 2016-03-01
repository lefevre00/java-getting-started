package org.friends.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.friends.app.model.User;

import spark.utils.Assert;

public class UserDao {

	private static List<User> userCache = new ArrayList<>();
    
    static{
    	userCache.add(new User("abdel.tamditi@amdm.fr","at"));
    	userCache.add(new User("william.verdeil@amdm.fr","wv"));
    	userCache.add(new User("michael.lefevre@amdm.fr","ml"));
    }		
	
	public User persist(User user) {
		Assert.notNull(user);
		userCache.add(user);
		return user;
	}

	public User findFirst(Predicate<User> predicate) {
		Optional<User> user = userCache.stream().filter(predicate).findFirst();
		return user.isPresent() ? user.get() : null;
	}
}
