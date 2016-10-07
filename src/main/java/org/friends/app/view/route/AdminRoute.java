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

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class AdminRoute extends AdminAuthRoute {

	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);

		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			String paramDebut = request.queryParams("dateDebut");
			LocalDate dateDebut = paramDebut != null ? DateUtil.stringToDate(paramDebut, Locale.FRANCE) : null;
			String paramFin = request.queryParams("dateFin");
			LocalDate dateFin = paramFin != null ? DateUtil.stringToDate(paramFin, Locale.FRANCE) : null;

			List<Place> datesPartages = placeService.getAllPlaceBetweenTwoDates(DateUtil.dateToString(dateDebut),
					DateUtil.dateToString(dateFin));
			if (!datesPartages.isEmpty()) {
				map.put("datesPartages", datesPartages);
			}
		}

		return new ModelAndView(map, Templates.ADMIN_PAGE);

	}
}
