package org.friends.app.view;

import static org.friends.app.Configuration.getPort;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.security.AccessControlException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.friends.app.Configuration;
import org.friends.app.model.Place;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.friends.app.service.impl.DateServiceBean;
import org.friends.app.util.DateUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

@Component
public class RoutesLoader {

	@Autowired
	private UserService userService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private DateServiceBean dateService;

	public void init(GenericApplicationContext context) {
		port(getPort());
		staticFileLocation("/public");

		/*
		 * Auto compress response Warning : commented because this break images
		 */
		// after((request, response) -> {
		// String header = request.raw().getHeader("Accept-Encoding");
		// if (header != null && header.contains("gzip"))
		// response.header("Content-Encoding", "gzip");
		// });
		exception(NullPointerException.class, (e, request, response) -> {
			e.printStackTrace();
			response.redirect(Routes.ERROR_PAGE);
		});

		exception(AccessControlException.class, (e, request, response) -> {
			response.redirect(Routes.LOGIN);
		});

		/*
		 * User login
		 */
		LoginRoute loginRoute = context.getBean(LoginRoute.class);
		get(Routes.LOGIN, loginRoute, new FreeMarkerEngine());
		post(Routes.LOGIN, loginRoute, new FreeMarkerEngine());

		get(Routes.DEFAULT, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			LocalDate now = DateUtil.now();
			List<Place> placesLibresToday = placeService.getAvailableByDate(now);
			List<Place> placesLibresDemain = placeService.getAvailableByDate(dateService.getNextWorkingDay(now));
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
		get(Routes.ERROR_PAGE, (req, res) -> {
			return new ModelAndView(Routes.getMap(req), "error.ftl");
		}, new FreeMarkerEngine());

		/*
		 * User register
		 */
		RegisterRoute registerRoute = context.getBean(RegisterRoute.class);
		get(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		post(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		get(Routes.TOKEN_VALIDATION, context.getBean(ValidTokenRoute.class), new FreeMarkerEngine());
		get(Routes.REGISTRED, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			map.put("title", "Utilisateur enregistré");
			map.put("message", "Veuillez suivre les indications qu'il comporte pour activer votre compte.");
			return new ModelAndView(map, "mail_send.ftl");
		}, new FreeMarkerEngine());

		/*
		 * Forgot password
		 */
		ForgottenPwdRoute forgottenPwdRoute = context.getBean(ForgottenPwdRoute.class);
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
		post(Routes.PASSWORD_NEW, context.getBean(PasswordTokenRoute.class), new FreeMarkerEngine());

		/*
		 * Places booking
		 */
		get(Routes.PLACE_SEARCH, context.getBean(SearchRoute.class), new FreeMarkerEngine());
		get(Routes.PLACE_BOOK, context.getBean(BookRoute.class), new FreeMarkerEngine());
		get(Routes.RESERVATIONS, context.getBean(BookedRoute.class), new FreeMarkerEngine());

		/*
		 * Share a place
		 */
		ShareRoute shareRoute = context.getBean(ShareRoute.class);
		get(Routes.PLACE_SHARE, shareRoute, new FreeMarkerEngine());
		post(Routes.PLACE_SHARE, shareRoute, new FreeMarkerEngine());

		/*
		 * User settings
		 */
		SettingRoute setting = context.getBean(SettingRoute.class);
		get(Routes.SETTINGS, setting, new FreeMarkerEngine());
		post(Routes.SETTINGS, setting, new FreeMarkerEngine());

		/*
		 * User Contact
		 */
		ContactRoute contactRoute = context.getBean(ContactRoute.class);
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
					response.cookie("/", Configuration.COOKIE, session.getCookie(), Configuration.COOKIE_DURATION,
							false);
				}
			}
		};
		after(Routes.RESERVATIONS, setCookieFilter);
		before(Routes.PLACE_SEARCH, setCookieFilter);
		before(Routes.PLACE_SHARE, setCookieFilter);
		before("/", setCookieFilter);
	}

	private User getAuthenticatedUser(Request request) {
		return request.session().attribute("user");
	}

	private void removeAuthenticatedUser(Request request) {
		request.session().removeAttribute("user");
	}
}
