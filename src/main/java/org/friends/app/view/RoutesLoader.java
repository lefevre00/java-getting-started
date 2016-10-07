/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.friends.app.view;

import static org.friends.app.ConfHelper.getPort;
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

import javax.annotation.PostConstruct;

import org.friends.app.ConfHelper;
import org.friends.app.DeployMode;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.model.UserSession;
import org.friends.app.service.DateService;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.route.AdminCreateUserRoute;
import org.friends.app.view.route.AdminRoute;
import org.friends.app.view.route.AdminShareRoute;
import org.friends.app.view.route.AuthenticatedRoute;
import org.friends.app.view.route.BookRoute;
import org.friends.app.view.route.BookedRoute;
import org.friends.app.view.route.ChangePwdRoute;
import org.friends.app.view.route.ContactRoute;
import org.friends.app.view.route.ForgottenPwdRoute;
import org.friends.app.view.route.LoginRoute;
import org.friends.app.view.route.PasswordTokenRoute;
import org.friends.app.view.route.RegisterRoute;
import org.friends.app.view.route.Routes;
import org.friends.app.view.route.SearchRoute;
import org.friends.app.view.route.SettingRoute;
import org.friends.app.view.route.ShareRoute;
import org.friends.app.view.route.StatisticsRoute;
import org.friends.app.view.route.UnregisterRoute;
import org.friends.app.view.route.UserEditRoute;
import org.friends.app.view.route.UsersListRoute;
import org.friends.app.view.route.ValidTokenRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

@Component
public class RoutesLoader {

	public static final String ROUTES_DIR = "routesDirectory";
	public static final String RESOURCES_DIR = "ressourcesDirectory";

	@Autowired
	private UserService userService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private DateService dateService;
	@Autowired
	ApplicationContext context;

	@PostConstruct
	public void init() {
		if (!DeployMode.STANDALONE.equals(ConfHelper.getDeployMode())) {
			port(getPort());
		}
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

		FreeMarkerEngine templateEngine = new FreeMarkerEngine();

		/*
		 * Admin Section
		 */
		AdminRoute adminRoute = context.getBean(AdminRoute.class);
		get(Routes.ADMIN_INDEX, adminRoute, templateEngine);
		post(Routes.ADMIN_INDEX, adminRoute, templateEngine);

		/*
		 * Liste des utilisateurs
		 */
		UsersListRoute usersList = context.getBean(UsersListRoute.class);
		get(Routes.USERS_LIST, usersList, templateEngine);
		post(Routes.USERS_LIST, usersList, templateEngine);

		/*
		 * Editer et modifier un utilisateur
		 */
		UserEditRoute userEditRoute = context.getBean(UserEditRoute.class);
		get(Routes.USER_EDIT, userEditRoute, templateEngine);
		post(Routes.USER_EDIT, userEditRoute, templateEngine);

		/*
		 * Libérer les places d'un utilisateur
		 */
		AdminShareRoute adminShareRoute = context.getBean(AdminShareRoute.class);
		get(Routes.ADMIN_SHARE, adminShareRoute, templateEngine);
		post(Routes.ADMIN_SHARE, adminShareRoute, templateEngine);	
		
		
		/*
		 * Création d'un nouvel utilisateur
		 */
		AdminCreateUserRoute adminCreateRoute = context.getBean(AdminCreateUserRoute.class);
		get(Routes.ADMIN_CREATE, adminCreateRoute, templateEngine);
		post(Routes.ADMIN_CREATE, adminCreateRoute, templateEngine);	
		

		/*
		 * Page accès interdit
		 */
		get(Routes.ACCESS_DENIED, (req, res) -> {
			return new ModelAndView(Routes.getMap(req), Templates.ACCESS_DENIED);
		}, templateEngine);

		/*
		 * User login
		 */
		LoginRoute loginRoute = context.getBean(LoginRoute.class);
		get(Routes.LOGIN, loginRoute, templateEngine);
		post(Routes.LOGIN, loginRoute, templateEngine);

		get(Routes.DEFAULT, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			map.put(RESOURCES_DIR, DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? "./" : "/");
			map.put(ROUTES_DIR, DeployMode.STANDALONE.equals(ConfHelper.getDeployMode()) ? "./" : "/");
			LocalDate now = dateService.getWorkingDay();
			List<Place> placesLibresToday = placeService.getAvailablesAtDate(now);
			List<Place> placesLibresDemain = placeService.getAvailablesAtDate(dateService.getNextWorkingDay(now));
			map.put("placesToday", placesLibresToday.size());
			map.put("labelFirstDay", DateUtil.dateToDayString(now));
			map.put("placesDemain", placesLibresDemain.size());
			map.put("labelSecondDay", DateUtil.dateToDayString(dateService.getNextWorkingDay(now)));

			return new ModelAndView(map, Templates.INDEX);
		}, templateEngine);

		/*
		 * Déconnexion
		 */
		get(Routes.LOGOUT, new AuthenticatedRoute() {
			@Override
			protected ModelAndView doHandle(Request request, Response response) {
				Map<String, Object> map = Routes.getMap(request);
				unauthenticatedUser(request);
				response.removeCookie(ConfHelper.COOKIE);
				return new ModelAndView(map, Templates.LOGOUT);
			}
		}, templateEngine);

		/*
		 * Error page
		 */
		get(Routes.ERROR_PAGE, (req, res) -> {
			return new ModelAndView(Routes.getMap(req), Templates.ERROR);
		}, templateEngine);

		/*
		 * User register
		 */
		RegisterRoute registerRoute = context.getBean(RegisterRoute.class);
		get(Routes.REGISTER, registerRoute, templateEngine);
		post(Routes.REGISTER, registerRoute, templateEngine);
		get(Routes.TOKEN_VALIDATION, context.getBean(ValidTokenRoute.class), templateEngine);
		get(Routes.REGISTRED, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			map.put("title", "Utilisateur enregistré");
			map.put("message", "Veuillez suivre les indications qu'il comporte pour activer votre compte.");
			return new ModelAndView(map, Templates.SEND_MAIL);
		}, templateEngine);
		get(Routes.UNREGISTER, context.getBean(UnregisterRoute.class), templateEngine);

		/*
		 * Forgot password
		 */
		ForgottenPwdRoute forgottenPwdRoute = context.getBean(ForgottenPwdRoute.class);
		get(Routes.PASSWORD_LOST, forgottenPwdRoute, templateEngine);
		post(Routes.PASSWORD_LOST, forgottenPwdRoute, templateEngine);
		get(Routes.PASSWORD_RESET, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			map.put("title", "Mot de passe en cours de modification");
			map.put("message", "Veuillez suivre les indications qu'il comporte pour définir un nouveau mot de passe.");
			return new ModelAndView(map, Templates.SEND_MAIL);
		}, templateEngine);
		get(Routes.PASSWORD_NEW, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			map.put("token", req.queryParams("tok"));
			return new ModelAndView(map, Templates.PASSWORD_NEW);
		}, templateEngine);
		post(Routes.PASSWORD_NEW, context.getBean(PasswordTokenRoute.class), templateEngine);

		/*
		 * Places booking
		 */
		get(Routes.PLACE_SEARCH, context.getBean(SearchRoute.class), templateEngine);
		get(Routes.PLACE_BOOK, context.getBean(BookRoute.class), templateEngine);
		get(Routes.RESERVATIONS, context.getBean(BookedRoute.class), templateEngine);

		/*
		 * Share a place
		 */
		ShareRoute shareRoute = context.getBean(ShareRoute.class);
		get(Routes.PLACE_SHARE, shareRoute, templateEngine);
		post(Routes.PLACE_SHARE, shareRoute, templateEngine);
		get(Routes.PLACE_BOOK, context.getBean(BookRoute.class), templateEngine);
		get(Routes.RESERVATIONS, context.getBean(BookedRoute.class), templateEngine);

		/*
		 * Statistics
		 */
		StatisticsRoute statsRoute = context.getBean(StatisticsRoute.class);
		get(Routes.PLACE_STATISTICS, statsRoute, templateEngine);
		post(Routes.PLACE_STATISTICS, statsRoute, templateEngine);

		/*
		 * User settings
		 */
		SettingRoute setting = context.getBean(SettingRoute.class);
		get(Routes.SETTINGS, setting, templateEngine);
		post(Routes.SETTINGS, setting, templateEngine);
		
		get(Routes.HISTORY, (req, res) -> {
			Map<String, Object> map = Routes.getMap(req);
			map.put("message", "Page en cours de réalisation");
			return new ModelAndView(map, Templates.ERROR);
		}, templateEngine);

		/*
		 * Change password
		 */
		ChangePwdRoute changePwdRoute = context.getBean(ChangePwdRoute.class);
		get(Routes.PASSWORD_CHANGE, changePwdRoute, templateEngine);
		post(Routes.PASSWORD_CHANGE, changePwdRoute, templateEngine);

		/*
		 * User Contact
		 */
		ContactRoute contactRoute = context.getBean(ContactRoute.class);
		post(Routes.MESSAGE_CONTACT, contactRoute, templateEngine);

		/*
		 * Set cookie if needed
		 */
		Filter setCookieFilter = (request, response) -> {
			User authUser = getAuthenticatedUser(request);
			if (authUser != null) {
				String cookie = request.cookie(ConfHelper.COOKIE);
				if (cookie == null) {
					UserSession session = userService.createSession(authUser);
					response.cookie("/", ConfHelper.COOKIE, session.getCookie(), ConfHelper.COOKIE_DURATION, false);
				}
			}
		};
		after(Routes.RESERVATIONS, setCookieFilter);
		before(Routes.PLACE_SEARCH, setCookieFilter);
		before(Routes.PLACE_SHARE, setCookieFilter);
		before(Routes.PLACE_STATISTICS, setCookieFilter);
		before("/", setCookieFilter);
	}

	private User getAuthenticatedUser(Request request) {
		return request.session().attribute("user");
	}

	private void unauthenticatedUser(Request request) {
		request.session().removeAttribute("user");
	}
}
