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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.friends.app.Configuration;
import org.friends.app.model.Place;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.view.route.AuthenticatedRoute;
import org.friends.app.view.route.BookRoute;
import org.friends.app.view.route.BookedRoute;
import org.friends.app.view.route.ContactRoute;
import org.friends.app.view.route.ForgottenPwdRoute;
import org.friends.app.view.route.LoginRoute;
import org.friends.app.view.route.PasswordTokenRoute;
import org.friends.app.view.route.RegisterRoute;
import org.friends.app.view.route.Routes;
import org.friends.app.view.route.SearchRoute;
import org.friends.app.view.route.SettingRoute;
import org.friends.app.view.route.ShareRoute;
import org.friends.app.view.route.ValidTokenRoute;

import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Application {

	private static Application instance;
	
	UserServiceBean userService = new UserServiceBean();
	PlaceServiceBean placeService = new PlaceServiceBean();

	public Application() {
		if (instance == null) instance = this;
	}

	public void start() throws SQLException, URISyntaxException {
		port(getPort());
		staticFileLocation("/public");

		/*
		 * Auto compress response 
		 * Warning : commented because this break images
		 */
		//		after((request, response) -> {
		//			String header = request.raw().getHeader("Accept-Encoding");
		//			if (header != null && header.contains("gzip"))
		//				response.header("Content-Encoding", "gzip");
		//		});

		exception(AccessControlException.class, (e, request, response) -> {
			response.redirect(Routes.LOGIN);
		});

		get(Routes.CHOICE_ACTION, new AuthenticatedRoute() {
			@Override
			protected ModelAndView doHandle(Request request, Response response) {
				return new ModelAndView(Routes.getMap(request), "actions.ftl");
			}
		}, new FreeMarkerEngine());

		get(Routes.DEFAULT, new AuthenticatedRoute() {
			@Override
			protected ModelAndView doHandle(Request request, Response response) {
				String dest = Routes.RESERVATIONS;
				User user = getAuthenticatedUser(request);
				if (user != null && user.getPlaceNumber() != null) {
					dest = Routes.CHOICE_ACTION;
				}
				response.redirect(dest);
				return new ModelAndView(null, "index.ftl");
			}
		}, new FreeMarkerEngine());

		/*
		 * User login 
		 */
		LoginRoute loginRoute = new LoginRoute();
		get(Routes.LOGIN, loginRoute, new FreeMarkerEngine());
		post(Routes.LOGIN, loginRoute, new FreeMarkerEngine());
//		get("/", (req, res) -> {
//			return new ModelAndView(Routes.getMap(req), "index.ftl");
//		}, new FreeMarkerEngine());
		
		get("/", (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			List<Place> placesLibresToday = placeService.getAvailableByDate(LocalDate.now());
			List<Place> placesLibresDemain = placeService.getAvailableByDate(LocalDate.now().plusDays(1));
			map.put("placesToday", placesLibresToday.size());
			map.put("placesDemain", placesLibresDemain.size());
			return new ModelAndView(map, "index.ftl");
		}, new FreeMarkerEngine());		
		
		
		/*
		 * Déconnexion
		 */
		get(Routes.LOGOUT, new AuthenticatedRoute() {
			@Override
			protected ModelAndView doHandle(Request request, Response response) {
				removeAuthenticatedUser(request);
				response.removeCookie(Configuration.COOKIE);
				return new ModelAndView(null, "logout.ftl");
			}
		}, new FreeMarkerEngine());


		/*
		 * Error page
		 */		
//		get(Routes.ERROR_PAGE, new AuthenticatedRoute() {
//			@Override
//			protected ModelAndView doHandle(Request request, Response response) {
//				return new ModelAndView(null, "error.ftl");
//			}
//		}, new FreeMarkerEngine());		
		
		
		
		
		
		get(Routes.ERROR_PAGE, (req, res) -> {
			return new ModelAndView(Routes.getMap(req), "error.ftl");
		}, new FreeMarkerEngine());		
		
		
		
		/* 
		 * User register 
		 */
		RegisterRoute registerRoute = new RegisterRoute();
		get(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		post(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		get(Routes.TOKEN_VALIDATION, new ValidTokenRoute(), new FreeMarkerEngine());
		get(Routes.REGISTRED, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
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
			Map<String, Object> map = Routes.getMap(req);
			map.put("title", "Mot de passe en cours de modification");
			map.put("message", "Veuillez suivre les indications qu'il comporte pour définir un nouveau mot de passe.");
			return new ModelAndView(map, "mail_send.ftl");
		}, new FreeMarkerEngine());
		get(Routes.PASSWORD_NEW, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
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
		 * User Contact
		 */
		ContactRoute contactRoute = new ContactRoute();
		post(Routes.MESSAGE_CONTACT, contactRoute, new FreeMarkerEngine());


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
		after(Routes.RESERVATIONS, setCookieFilter);
		before(Routes.PLACE_SEARCH, setCookieFilter);
		before(Routes.PLACE_SHARE, setCookieFilter);
		before("/", setCookieFilter);
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
