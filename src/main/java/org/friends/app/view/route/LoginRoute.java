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
package org.friends.app.view.route;

import java.util.Map;
import java.util.logging.Logger;

import org.friends.app.ConfHelper;
import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Page de Login
 * 
 * @author michael lefevre
 */
@Component
public class LoginRoute implements TemplateViewRoute {

	private static final String KEY_EMAIL = "email";

	@Autowired
	private UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = Routes.getMap(request);
		User user = request.session().attribute("user");
		if(("ok".equalsIgnoreCase(request.queryParams("activation"))) 
				&& !ConfHelper.INSCRIPTION_LIBRE){
			map.put(Routes.KEY_INFO, "Inscription Validée, vous pouvez vous connecter.");
		}
		if (user != null) {
			Routes.redirect(user, response, false);
		} else if ("POST".equalsIgnoreCase(request.requestMethod())) {	
			onLogin(request, response, map);
		}

		return new ModelAndView(map, Templates.LOGIN);
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) {

		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");

		// En cas d'erreur
		map.put(KEY_EMAIL, email);

		User user = null;

		try {
			user = userService.authenticate(email, pwd);

			if (user != null) {
				boolean isAdmin = false;
				if (ConfHelper.ADMIN_MAIL.equals(email)){
					Logger.getLogger("login").info("Admin logged in : " + email);
					map.put("admin", "true");
					isAdmin = true;
				}
				else{
					Logger.getLogger("login").info("user logged in : " + user.getEmailAMDM());
				}
				addAuthenticatedUser(request, user);
				Routes.redirect(user, response, isAdmin);
			} else {
				map.put(Routes.KEY_ERROR, "Utilisateur introuvable !");
			}

		} catch (Exception e) {
			map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
		}
	}

	private void addAuthenticatedUser(Request request, User user) {
		request.session().attribute("user", user);
	}
}
