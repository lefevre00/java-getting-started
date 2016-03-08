package org.friends.app.view;

import org.friends.app.model.User;

import spark.Response;

public interface Routes {
	
	public static void redirect(User user, Response response) {
		String dest = PLACE_SEARCH;
		if (user.getPlaceNumber() != null)
			dest = PLACE_SHARE;
		response.redirect(dest);
	}

	String LOGOUT = "/user/logout";
	String REGISTER = "/user/new";
	String LOGIN = "/user/login";
	String PASSWORD_LOST = "/user/forget";
	
	String PLACE_SEARCH = "/protected/search";
	String PLACE_SHARE = "/protected/sharePlace";
	String CHOICE_ACTION = "/protected/choice";
}
