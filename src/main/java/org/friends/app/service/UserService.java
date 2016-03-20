package org.friends.app.service;

import java.net.URISyntaxException;
import java.sql.SQLException;

import org.friends.app.model.User;

public interface UserService {
	
	public static String EMAIL_ERROR = "email.error";
	public static String EMAIL_REQUIRED = "email.required";
	public static String EMAIL_UNKNOWN = "email.unknown";
	
	public static String PWD_ERROR = "password.error";
	public static String PWD_REQUIRED = "passwor.required";
	
	public static String USER_DISABLE = "user.disable";
	
	public User userAuthentication (String email, String pwd) throws Exception;
	
	public User findUserByEmail(String email) throws SQLException, URISyntaxException;
	
	public User findUserByCookie(String cookie) throws SQLException, URISyntaxException;
	
	public User create(User user, String applicationHost) throws Exception;
}
