package org.friends.app.view.route;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.ConfHelper;
import org.friends.app.DeployMode;
import org.friends.app.model.User;
import org.friends.app.view.RoutesLoader;

import spark.Request;
import spark.Response;

public class Routes {

	public static void redirect(User user, Response response, boolean isAdmin) {
		
		
		String directory = DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? ".." : "";
		String dest = ADMIN_INDEX;
		if(!isAdmin) {
			dest = RESERVATIONS;
			if (user == null)
				dest = Routes.LOGIN;
			else if (user.getPlaceNumber() != null)
				dest = PLACE_SHARE;
		}
		response.redirect(directory + dest);
		
		/*
		String directory = DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? ".." : "";
		String dest = RESERVATIONS;
		if (user == null)
			dest = Routes.LOGIN;
		else if (user.getPlaceNumber() != null)
			dest = PLACE_SHARE;
		
		else
			dest = ADMIN_INDEX;
		response.redirect(directory + dest);
		 * */
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
		map.put(RoutesLoader.RESOURCES_DIR, DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? "../" : "/");
		map.put(RoutesLoader.ROUTES_DIR, DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? "../" : "/");
		if (user != null) {
			map.put("mail", user.getEmailAMDM());
			if (user.getPlaceNumber() != null) {
				map.put("canShare", "true");
				map.put("placeNumber", user.getPlaceNumber());
			}

			// Données login admin dans fichier properties
			if (ConfHelper.ADMIN_MAIL.equalsIgnoreCase(user.getEmailAMDM())) {
				map.put("admin", "true");
			}
		}

		return map;
	}

	public static String PARAM_TOKEN_VALUE = "tok";

	/*
	 * Accessible for everyone
	 */
	public static String DEFAULT = "/";
	public static String LOGIN = "/user/login";
	public static String REGISTER = "/user/new";
	public static String TOKEN_VALIDATION = "/user/validate";
	public static String REGISTRED = "/user/registred";
	public static String PASSWORD_LOST = "/user/forget";
	public static String PASSWORD_RESET = "/user/reset";
	public static String PASSWORD_NEW = "/user/pwd";
	public static String PASSWORD_SETTED = "/user/setted";

	public static String ERROR_PAGE = "/user/error";
	public static String MESSAGE_CONTACT = "/user/contact";

	/*
	 * Accessible if authenticated
	 */
	public static String LOGOUT = "/user/logout";
	public static String PLACE_SEARCH = "/protected/search";
	public static String PLACE_SHARE = "/protected/share";
	public static String PLACE_STATISTICS = "/protected/statistics";
	public static String PLACE_BOOK = "/protected/book/:date/:place_id";
	public static String RESERVATIONS = "/protected/booked";
	public static String SETTINGS = "/protected/setting";
	public static String UNREGISTER = "/protected/unregister";

	public static String HISTORY = "/protected/history";
	public static String PASSWORD_CHANGE = "/protected/change_pwd";
	public static String ADMIN_INDEX = "/protected/adminPage";

	public static String ACCESS_DENIED = "/protected/accessDenied";
	public static String USERS_LIST = "/protected/usersList";
	public static String USER_EDIT = "/protected/userEdit/:user_id";

	/*
	 * Model map key
	 */
	public static String KEY_ERROR = "error";
	public static String KEY_INFO = "info";
	public static String KEY_SUCCESS = "success";
}
