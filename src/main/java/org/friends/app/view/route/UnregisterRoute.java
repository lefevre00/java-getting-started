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

import java.util.List;
import java.util.Map;

import org.friends.app.ConfHelper;
import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.omg.CORBA.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class UnregisterRoute extends AuthenticatedRoute {

	@Autowired
	UserService userService;

	@Override
	protected ModelAndView doHandle(Request request, Response response) {
		Map<String, Object> map = Routes.getMap(request);
		User userLogue = getUser(request);
		String email_user = null;
		try {
			if(!isAdmin(request)) {
				userService.delete(userLogue);
			} else {
				email_user = request.queryParams("email_user");
				if(email_user != null) {
					User userToDelete = userService.findUserByEmail(email_user);
					userService.delete(userToDelete);
				}
			}
		} catch (UnknownUserException | DataIntegrityException e) {
			if(!isAdmin(request)) {
				User user = getUser(request);
				map.put("user", user);
				if (user.getPlaceNumber() != null) {
					map.put("placeNumber", user.getPlaceNumber().toString());
				}
				map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
				return new ModelAndView(map, Templates.SETTING);
			} else {
				map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
				return new ModelAndView(map, Templates.ADMIN_PAGE);
			}
		}
		if(!isAdmin(request)) {
			response.redirect(ConfHelper.complementUrl() + Routes.LOGOUT);
			return new ModelAndView(map, Templates.LOGOUT);
		} else {
			String message = Messages.get("admin.delete.user");
			map.put(Routes.KEY_SUCCESS, message.replaceAll("#email_user#", email_user)); 
			List<User> allUsers = userService.getAllUsersWithoutAdmin();
			map.put("inscriptionLibre", ConfHelper.INSCRIPTION_LIBRE ? null : "oui");
			map.put("usersList", allUsers);
			return new ModelAndView(map, Templates.USERS_LIST);
		}
	}
}