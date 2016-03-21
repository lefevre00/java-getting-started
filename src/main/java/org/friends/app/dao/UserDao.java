package org.friends.app.dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.friends.app.model.User;

import spark.utils.Assert;

public class UserDao {

	private static List<User> userCache = new ArrayList<User>();
    
	public User persist(User user) throws SQLException, URISyntaxException {
		Assert.notNull(user);
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(Database.sqlUSERCreate);
		stmt.setString(0, user.getEmailAMDM());
		stmt.setInt(1, user.getPlaceNumber().intValue());
		stmt.setString(2, user.getPwd());
		stmt.setString(3, user.getTokenPwd());
		userCache.add(user);
		return user;
	}

	public User findFirst(Predicate<User> predicate) throws SQLException, URISyntaxException {
		Connection conn = Database.getConnection();		
		Optional<User> user = userCache.stream().filter(predicate).findFirst();
		return user.isPresent() ? user.get() : null;
	}
}
