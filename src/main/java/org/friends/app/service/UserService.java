package org.friends.app.service;

import org.friends.app.model.User;

public interface UserService {
	
	public static String EMAIL_ERROR = "errEmail";
	
	public static String PWD_ERROR = "errPwd";
	
	
	public User userAuthentication (String email, String pwd) throws Exception;
	
	
	public User findUserByEmail(String email);
	
	
	public User findUserByCookie(String cookie);
	
	
	public void create(User user) throws Exception;

}
