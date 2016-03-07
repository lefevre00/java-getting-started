package org.friends.app.view;

import org.friends.app.model.User;

import spark.Response;

public interface Routes {
	
	public static void redirect(User user, Response response) {
		String dest = "/protected/search";
		if (user.getPlaceNumber() != null)
			dest = "/protected/sharePlace";
		response.redirect(dest);
	}

	String LOGOUT = "/user/logout";
	String REGISTER = "/user/new";
	String LOGIN = "/user/login";
}
