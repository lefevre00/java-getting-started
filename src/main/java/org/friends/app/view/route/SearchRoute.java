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

import static org.friends.app.util.DateUtil.dateToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.DateService;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class SearchRoute extends AuthenticatedRoute {

	@Autowired
	private DateService dateService;
	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request req, Response resp) {

		String paramDate = req.queryParams("day");
		String dateRecherchee = paramDate != null ? paramDate : dateToString(dateService.getNextWorkingDay());
		LocalDate dateRechercheeAsDate = DateUtil.stringToDate(dateRecherchee);

		Map<String, Object> map = Routes.getMap(req);
		map.put("dateBook", dateRecherchee);
		map.put("dateRecherche", DateUtil.dateToFullString(dateRechercheeAsDate));

		if(!dateService.isSearchDateValid(dateRechercheeAsDate)){
			map.put("message", "Les places ne sont pas encore réservables ");
			return new ModelAndView(map, Templates.SEARCH);
		}
		
		User user = getUser(req);
		Place bookedPlace = getBookedPlace(user, dateRechercheeAsDate);
		if (bookedPlace != null) {
			// L'utilisateur a déjà réservé une place ce jour là
			map.put("message", "Vous avez déjà réservé la place " + bookedPlace.getPlaceNumber());
		} else {
			List<Place> places = getPlaces(dateRechercheeAsDate);
			if (!places.isEmpty()) {
				map.put("place", places.get(0));
			}
		}

		return new ModelAndView(map, Templates.SEARCH);
	}

	private List<Place> getPlaces(LocalDate dateRecherche) {
		return placeService.getAvailablesAtDate(dateRecherche);
	}

	private Place getBookedPlace(User user, LocalDate dateRecherche) {
		return placeService.getBookedPlaceByUserAtDate(user, dateRecherche);
	}
}
