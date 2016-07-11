package org.friends.app.view.route;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.friends.app.model.User;

import spark.Request;
import spark.Response;

public interface Routes {
	
	static final String APPLICATION_PROPERTIES = "application.properties";

	public static void redirect(User user, Response response) {
		String dest = RESERVATIONS;
		if (user == null)
			dest = Routes.LOGIN;
		else if (user.getPlaceNumber() != null)
			dest = PLACE_SHARE;
		else
			dest= ADMIN_INDEX;
		response.redirect(dest);
	}

	/**
	 * Build the default map object to tell the template that the user is logged
	 * in.
	 * 
	 * @return
	 */
	public static Map<String, Object> getMap(Request request) {
		Map<String, Object> map = new HashMap<>();

		User user = request.session().attribute("user");
		if (user != null) {
			map.put("mail", user.getEmailAMDM());
			if (user.getPlaceNumber() != null) {
				map.put("canShare", "true");
				map.put("placeNumber", user.getPlaceNumber());
			}

			// Données login admin dans fichier properties
			Properties tmp = new Properties();
			try {
				tmp.load(LoginRoute.class.getResourceAsStream(APPLICATION_PROPERTIES));
			} catch (IOException e) {
				System.out.println("erreur lecture application.properties");
			}
			Properties properties = tmp;

			if((properties.getProperty("admin.email")).equalsIgnoreCase(user.getEmailAMDM())) {
			    map.put("admin", "true");
			}
		}

		return map;
	}

	String PARAM_TOKEN_VALUE = "tok";

	/*
	 * Accessible for everyone
	 */
	String DEFAULT = "/";
	String LOGIN = "/user/login";
	String REGISTER = "/user/new";
	String TOKEN_VALIDATION = "/user/validate";
	String REGISTRED = "/user/registred";
	String PASSWORD_LOST = "/user/forget";
	String PASSWORD_RESET = "/user/reset";
	String PASSWORD_NEW = "/user/pwd";
	String PASSWORD_SETTED = "/user/setted";

	String ERROR_PAGE = "/user/error";
	String MESSAGE_CONTACT = "/user/contact";

	/*
	 * Accessible if authenticated
	 */
	String LOGOUT = "/user/logout";
	String PLACE_SEARCH = "/protected/search";
	String PLACE_SHARE = "/protected/share";
	String PLACE_STATISTICS = "/protected/statistics";
	String PLACE_BOOK = "/protected/book/:date/:place_id";
	String RESERVATIONS = "/protected/booked";
	String SETTINGS = "/protected/setting";
	String UNREGISTER = "/protected/unregister";

	String HISTORY = "/protected/history";
	String PASSWORD_CHANGE = "/protected/change_pwd";
	String ADMIN_INDEX = "/protected/adminPage";

	String ACCESS_DENIED = "/protected/accessDenied";
	String USERS_LIST = "/protected/usersList";
	String USER_EDIT = "/protected/userEdit/:user_id";

	/*
	 * Model map key
	 */
	String KEY_ERROR = "error";
	String KEY_INFO = "info";
	String KEY_SUCCESS = "success";

}
