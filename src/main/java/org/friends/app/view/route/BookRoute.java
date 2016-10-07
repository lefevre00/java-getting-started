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

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.BookingException;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class BookRoute extends AuthenticatedRoute {

	@Autowired
	PlaceService service;

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		User user = getUser(request);
		Map<String, Object> map = Routes.getMap(request);

		String date = request.queryParams("date");
		String place = request.queryParams("place");
		if (StringUtils.isEmpty(date)) {
			date = DateUtil.dateToString(DateUtil.now());
		}

		if (StringUtils.isEmpty(place)) {
			map.put("dateRecherche", "N/C");
			map.put("message", "Merci de sélectionner une place et une date pour compléter la réservation.");
			map.put("numeroPlace", "N/C");
		} else {
			try {
				map.put("dateRecherche", date);
				Place booked = service.book(date, user, place);
				map.put("message", "");
				map.put("numeroPlace", booked.getPlaceNumber().toString());
			} catch (BookingException e) {
				map.put("message", "Vous avez déjà réservé une place pour le jour demandée.");
				map.put("numeroPlace", "");
				map.put("dateRecherche", "");
			}
		}

		return new ModelAndView(map, Templates.BOOK);
	}
}
