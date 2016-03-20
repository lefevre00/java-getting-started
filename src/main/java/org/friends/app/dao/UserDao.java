package org.friends.app.dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		stmt.setString(1, user.getEmailAMDM());
		stmt.setInt(2, user.getPlaceNumber() != null ? user.getPlaceNumber().intValue() : 0);
		stmt.setString(3, user.getPwd());
		stmt.setString(4, user.getTokenPwd());
		stmt.execute();
		userCache.add(user);
		return user;
	}

	public User findFirst(Predicate<User> predicate) throws SQLException, URISyntaxException {
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(Database.sqlUSERFindByEmail);
	
		Optional<User> user = userCache.stream().filter(predicate).findFirst();
		stmt.setString(0, user.get().getEmailAMDM());
		ResultSet rs = stmt.executeQuery();
		User userFind = null;
		while (rs.next()) {
			userFind= new User(rs.getString("email"), rs.getString("password"), rs.getInt("email"));
			
		}
//		User user1 = user.isPresent() ? user.get() : null;
//		PreparedStatement stmt = conn.prepareStatement(Database.sqlUSERFindByEmail);
//		stmt.setString(1, user1.getEmailAMDM());
		
		return userFind;
	}
}
