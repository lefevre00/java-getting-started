package org.friends.app.view.route;

import org.friends.app.model.User;

import spark.Response;

public interface Routes {
	
	public static void redirect(User user, Response response) {
		String dest = RESERVATIONS;
		if (user == null)
			dest = Routes.LOGIN;
		else if (user.getPlaceNumber() != null)
			dest = PLACE_SHARE;
		response.redirect(dest);
	}

	String PARAM_TOKEN_VALUE = "tok";

	/*
	 * Accessible for everyone
	 */
	String LOGIN = "/user/login";
	String LOGOUT = "/user/logout";
	String REGISTER = "/user/new";
	String TOKEN_VALIDATION = "/user/validate";
	String REGISTRED = "/user/registred";
	String PASSWORD_LOST = "/user/forget";
	String PASSWORD_RESET = "/user/reset";
	String PASSWORD_NEW = "/user/pwd";
	String PASSWORD_SETTED = "/user/setted";
	
	String ERROR_PAGE = "/user/error";
	/*
	 * Accessible if authenticated
	 */
	String DEFAULT = "/protected/";
	String PLACE_SEARCH = "/protected/search";
	String PLACE_SHARE = "/protected/share";
	String CHOICE_ACTION = "/protected/choice";
	String PLACE_BOOK = "/protected/book/:date/:place_id";
	String RESERVATIONS = "/protected/booked";
	String SETTINGS = "/protected/setting";
	
}
