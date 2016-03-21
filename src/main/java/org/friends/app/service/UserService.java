package org.friends.app.service;

import org.friends.app.model.User;

public interface UserService {
	
	public static String EMAIL_ERROR = "email.error";
	public static String EMAIL_REQUIRED = "email.required";
	public static String EMAIL_UNKNOWN = "email.unknown";
	
	public static String PWD_ERROR = "password.error";
	public static String PWD_REQUIRED = "password.required";
	
	public static String USER_DISABLE = "user.disable";
	
	public User userAuthentication (String email, String pwd) throws Exception;
	
	public User findUserByEmail(String email);
	
	public User findUserByCookie(String cookie);
	
	public User create(User user, String applicationHost) throws Exception;
}
