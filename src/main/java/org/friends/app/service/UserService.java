package org.friends.app.service;

import org.friends.app.model.User;

public interface UserService {
	
	public static String EMAIL_REQUIRED = "requiredEmail";
	
	public static String PWD_REQUIRED = "requiredPwd";
	
	public static String PWD_ERROR = "errPwd";
	
	public static String EMAIL_ERROR = "errEmail";
	
	public User userAuthentication (String email, String pwd) throws Exception;
	
	public User findUserByEmail(String email);
	
	public User findUserByCookie(String cookie);
	
	public User create(User user, String applicationHost) throws Exception;
}
