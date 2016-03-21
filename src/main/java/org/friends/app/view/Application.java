package org.friends.app.view;

import static org.friends.app.Configuration.getPort;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.net.URISyntaxException;
import java.security.AccessControlException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.friends.app.Configuration;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.view.route.BookRoute;
import org.friends.app.view.route.BookedRoute;
import org.friends.app.view.route.ForgottenPwdRoute;
import org.friends.app.view.route.LoginRoute;
import org.friends.app.view.route.PasswordTokenRoute;
import org.friends.app.view.route.RegisterRoute;
import org.friends.app.view.route.Routes;
import org.friends.app.view.route.SearchRoute;
import org.friends.app.view.route.ShareRoute;
import org.friends.app.view.route.ValidTokenRoute;

import com.heroku.sdk.jdbc.DatabaseUrl;

import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;
import spark.utils.StringUtils;

public class Application {

	private static Application instance;
	UserServiceBean userService = new UserServiceBean();

	public Application() {
		if (instance == null) instance = this;
	}

	public void start() throws SQLException, URISyntaxException {
		port(getPort());
		staticFileLocation("/public");

		/*
		 * Auto compress response 
		 */
		after((request, response) -> {
			String header = request.raw().getHeader("Accept-Encoding");
			if (header != null && header.contains("gzip"))
				response.header("Content-Encoding", "gzip");
		});
		
		exception(AccessControlException.class, (e, request, response) -> {
		    response.status(401);
		    response.body("User not authenticated");
		});
		
		/*
		 * Controle that user first logged in
		 */
		Filter checkLoggedIn = new Filter() {
			@Override
			public void handle(Request request, Response response) throws Exception {
				boolean userFound = false;

				// 1 : try to find user in session
				User authenticatedUser = getAuthenticatedUser(request);
				if (authenticatedUser != null) {
					userFound = true;
				} else {
					
					// 2. Try to find user using cookie
					String cookie = request.cookie(Configuration.COOKIE);
					if (!StringUtils.isEmpty(cookie)) {
						
						User user = userService.findUserByCookie(cookie);
						if (user != null) {
							request.session().attribute("user", user);
							userFound = true;
						} else {
							// Clean cookie if no user
							response.removeCookie(Configuration.COOKIE);
						}
					}
				}
				
				if (!userFound) {
					response.redirect(Routes.LOGIN);
				}
			}
		};
		before("/protected/*", checkLoggedIn); 
		before("/", checkLoggedIn);

		get(Routes.CHOICE_ACTION, (request, response) -> {
			return new ModelAndView(null, "index.ftl");
		}, new FreeMarkerEngine());
			
		get(Routes.DEFAULT, (request, response) -> {
			String dest = Routes.RESERVATIONS;
			User user = getAuthenticatedUser(request);
			if (user != null && user.getPlaceNumber() != null) {
				dest = Routes.CHOICE_ACTION;
			}
			response.redirect(dest);
			return new ModelAndView(null, "index.ftl");
		}, new FreeMarkerEngine());

		/*
		 * User login 
		 */
		LoginRoute loginRoute = new LoginRoute();
		get(Routes.LOGIN, loginRoute, new FreeMarkerEngine());
		post(Routes.LOGIN, loginRoute, new FreeMarkerEngine());

		/*
		 * Déconnexion
		 */
		get(Routes.LOGOUT, (req, res) -> {
			removeAuthenticatedUser(req);
			res.removeCookie(Configuration.COOKIE);
			return null;
		});

		/* 
		 * User register 
		 */
		RegisterRoute registerRoute = new RegisterRoute();
		get(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		post(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		get(Routes.TOKEN_VALIDATION, new ValidTokenRoute(), new FreeMarkerEngine());
		get(Routes.REGISTRED, (req, res) -> {
			Map<String, String> map = new HashMap<>();
			map.put("title", "Utilisateur enregistré");
			map.put("message", "Veuillez suivre les indications qu'il comporte pour activer votre compte.");
			return new ModelAndView(map, "mail_send.ftl");
		}, new FreeMarkerEngine());

		/*
		 * Forgot password
		 */
		ForgottenPwdRoute forgottenPwdRoute = new ForgottenPwdRoute();
		get(Routes.PASSWORD_LOST, forgottenPwdRoute, new FreeMarkerEngine());
		post(Routes.PASSWORD_LOST, forgottenPwdRoute, new FreeMarkerEngine());
		get(Routes.PASSWORD_RESET, (req, res) -> {
			Map<String, String> map = new HashMap<>();
			map.put("title", "Mot de passe en cours de modification");
			map.put("message", "Veuillez suivre les indications qu'il comporte pour définir un nouveau mot de passe.");
			return new ModelAndView(map, "mail_send.ftl");
		}, new FreeMarkerEngine());
		get(Routes.PASSWORD_NEW, (req, res) -> {
			Map<String, String> map = new HashMap<>();
			map.put("token", req.queryParams("tok"));
			return new ModelAndView(map, "pwd_new.ftl");
		}, new FreeMarkerEngine());
		post(Routes.PASSWORD_NEW, new PasswordTokenRoute(), new FreeMarkerEngine());
		/*
		 * Places booking 
		 */
		get(Routes.PLACE_SEARCH, new SearchRoute(), new FreeMarkerEngine());
		get(Routes.PLACE_BOOK, new BookRoute(), new FreeMarkerEngine());
		get(Routes.RESERVATIONS, new BookedRoute(), new FreeMarkerEngine());

		/*
		 * Share a place
		 */
		ShareRoute shareRoute = new ShareRoute();
		get(Routes.PLACE_SHARE, shareRoute, new FreeMarkerEngine()); 
		post(Routes.PLACE_SHARE, shareRoute, new FreeMarkerEngine());//(req, res) -> "Vous libérez la place   " + req.queryParams("number") +" du " + req.queryParams("dateDebut") +" du " + req.queryParams("dateFin"));

		/*
		 * User settings
		 */
		SettingRoute setting = new SettingRoute(); 
		get(Routes.SETTINGS, setting, new FreeMarkerEngine()); 
		post(Routes.SETTINGS, setting, new FreeMarkerEngine()); 
		
		
		/*
		 * Set cookie if needed
		 */
		Filter setCookieFilter = (request, response) -> {
			User authUser = getAuthenticatedUser(request); 
			if (authUser != null) {
				String cookie = request.cookie(Configuration.COOKIE);
				if (cookie == null) {
					Session session = userService.createSession(authUser);
					response.cookie("/", Configuration.COOKIE, session.getCookie(), Configuration.COOKIE_DURATION, false);
				}
			}
		};
		after(Routes.PLACE_SEARCH, setCookieFilter);
		after(Routes.PLACE_SHARE, setCookieFilter);
		after("/", setCookieFilter);
		
		/* Intercept 404 */
		get("*", (request, response) -> {
			response.redirect(Routes.LOGIN);
			return "404";
		});
	}

	protected Connection getConnection() throws SQLException, URISyntaxException {
		return DatabaseUrl.extract().getConnection();
	}

	public static Application instance() {
		return instance;
	}
	
	private User getAuthenticatedUser(Request request) {
		return request.session().attribute("user");
	}

	private void removeAuthenticatedUser(Request request) {
		request.session().removeAttribute("user");
	}
}
