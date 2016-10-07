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

import java.security.AccessControlException;

import org.friends.app.ConfHelper;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

@Component
public abstract class AuthenticatedRoute implements TemplateViewRoute {

	@Autowired
	private UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		checkAuthenticated(request, response);
		return doHandle(request, response);
	}

	protected abstract ModelAndView doHandle(Request request, Response response);

	protected final void checkAuthenticated(Request request, Response response) {
		User user = getUser(request);

		// 1 : try to find user in session
		if (user != null)
			return;

		// 2. Try to find user using cookie
		boolean userFound = false;
		String cookie = request.cookie(ConfHelper.COOKIE);

		// Lors du refresh sur logout, userService == null
		if (!StringUtils.isEmpty(cookie) && userService != null) {
			user = userService.findUserByCookie(cookie);
			if (user != null) {
				request.session().attribute("user", user);
				userFound = true;
			} else {
				// Clean cookie if no user
				response.removeCookie(ConfHelper.COOKIE);
			}
		}

		// Stop route
		if (!userFound) {
			throw new AccessControlException("User not authenticated");
		}
	}

	protected User getUser(Request request) {
		return request.session().attribute("user");
	}

	protected boolean isAdmin(Request request) {
		if (request == null)
			return false;
		User user = getUser(request);
		if (user == null)
			return false;
		return ConfHelper.ADMIN_MAIL.equalsIgnoreCase(user.getEmailAMDM());
	}
}
