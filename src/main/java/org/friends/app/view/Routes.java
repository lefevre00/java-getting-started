package org.friends.app.view;

import org.friends.app.model.User;

import spark.Response;
import spark.utils.StringUtils;

public interface Routes {
	
	public static void redirect(User user, Response response) {
		String dest = RESERVATIONS;
		if (!StringUtils.isEmpty(user.getPlaceNumber()))
			dest = PLACE_SHARE;
		response.redirect(dest);
	}

	String TOKEN_PARAM = "tok";

	/*
	 * Accessible for everyone
	 */
	String LOGOUT = "/user/logout";
	String REGISTER = "/user/new";
	String LOGIN = "/user/login";
	String PASSWORD_LOST = "/user/forget";
	String TOKEN_VALIDATION = "/user/validate";
	String REGISTRED = "/user/registred";
	
	/*
	 * Accessible if authenticated
	 */
	String PLACE_SEARCH = "/protected/search";
	String PLACE_SHARE = "/protected/sharePlace";
	String CHOICE_ACTION = "/protected/choice";
	String PLACE_BOOK = "/protected/book/:date/:place_id";
	String RESERVATIONS = "/protected/booked";
}
