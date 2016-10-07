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

import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class SettingRoute extends AuthenticatedRoute {

	private static final String ERROR = "error";
	private static final String INFO = "info";

	@Autowired
	UserService userService;

	@Override
	protected ModelAndView doHandle(Request request, Response response) {

		String messageKey = null;
		String messageId = null;

		User user = getUser(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			boolean doUpdate = true;

			String placeParam = request.queryParams("placeNumber");
			Integer place = null;
			if (StringUtils.isNotEmpty(placeParam)) {
				try {
					place = Integer.valueOf(placeParam);
				} catch (NumberFormatException e) {
					messageKey = ERROR;
					messageId = "number.invalid";
					doUpdate = false;
				}
			}

			if (doUpdate) {
				boolean updated = false;
				try {
					user = userService.changePlace(user, place);
					updated = true;
				} catch (DataIntegrityException e) {
					messageKey = ERROR;
					messageId = e.getMessage();
				}

				if (updated) {
					request.session().attribute("user", user);
					messageKey = INFO;
					messageId = "update.ok";
				}
			}
		}

		Map<String, Object> map = Routes.getMap(request);
		map.put("user", user);
		if (messageKey != null) {
			map.put(messageKey, Messages.get(messageId));
		}
		return new ModelAndView(map, Templates.SETTING);
	}
}
