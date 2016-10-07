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

import org.friends.app.ConfHelper;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.DateService;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UnshareException;
import org.friends.app.service.UserService;
import org.friends.app.util.DateUtil;
import org.friends.app.validator.EmailValidator;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class ShareRoute extends AuthenticatedRoute {

	@Autowired
	private DateService dateService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private UserService userService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);
		User user = getUser(request);

		if (user.getPlaceNumber() == null) {
			throw new RuntimeException("A user without place cannot share a place");
		}

		// Premier jour reservable
		LocalDate jour1 = dateService.getWorkingDay();
		Place canShareToday = placeService.isPlaceShared(user.getPlaceNumber(), DateUtil.dateToString(jour1));
		map.put("canShareToday", canShareToday);
		map.put("jourProchaineLiberation", DateUtil.dateToString(jour1, Locale.FRANCE));
		map.put("libelleJourProchaineLiberation", DateUtil.dateToMediumString(jour1));

		// Deuxieme jour reservable
		LocalDate jour2 = dateService.getNextWorkingDay(jour1);
		canShareToday = placeService.isPlaceShared(user.getPlaceNumber(), DateUtil.dateToString(jour2));
		map.put("canShareTomorrow", canShareToday);
		map.put("jourDeuxiemeLiberation", DateUtil.dateToString(jour2, Locale.FRANCE));
		map.put("libelleJourDeuxiemeLiberation", DateUtil.dateToMediumString(jour2));

		// Messages
		String liberationOk = request.queryParams("liberation");
		if (!StringUtils.isEmpty(liberationOk) && "ok".equalsIgnoreCase(liberationOk)) {
			map.put("success", "Libération effectuée");
		}

		String annulationOk = request.queryParams("annulation");
		if (!StringUtils.isEmpty(annulationOk) && "ok".equalsIgnoreCase(annulationOk)) {
			map.put("success", "Annulation effectuée");
		}

		/*
		 * Partage de places
		 */
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			LocalDate dateDebut = request.queryParams("dateDebut") != null
					? DateUtil.stringToDate(request.queryParams("dateDebut"), Locale.FRANCE) : null;
			LocalDate dateFin = request.queryParams("dateFin") != null
					? DateUtil.stringToDate(request.queryParams("dateFin"), Locale.FRANCE) : null;
			
			// Email de l'occupant de la place
			String emailOccupant = request.queryParams("emailOccupant");

			boolean retour = false;
			User userOccupant = null;

			if (StringUtils.isNotEmpty(emailOccupant)){

				// Email validator
				if (!EmailValidator.isValid(emailOccupant)){
					map.put("message", "L'email saisi est incorrect !");
					return new ModelAndView(map, Templates.ERROR);
				}
				else {
					// On vérifie que l'utilisateur occupant existe bien en base
					userOccupant = userService.findUserByEmail(emailOccupant);
					if(userOccupant == null) {
						map.put("message", "L'utilisateur n'est pas connu !");
						return new ModelAndView(map, Templates.ERROR);
					}
				}
			}
			
			try {
				retour = placeService.sharePlaces(user, dateDebut, dateFin, emailOccupant);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "Une erreur est survenue lors de l'enregistrement de données !");
				return new ModelAndView(map, Templates.ERROR);
			}
			
			if (!retour) {
				if (StringUtils.isNotEmpty(emailOccupant) && userOccupant == null){
					map.put("message", "Utilisateur introuvable dans notre base de données !");
				}
				else if (dateDebut.equals(dateFin)) {
					map.put("message", "Vous avez déjà partagé votre place pour le "
							+ DateUtil.dateToString(dateDebut, Locale.FRANCE));
				} else {
					map.put("message", "Vous avez déjà partagé ces dates !");
				}
				return new ModelAndView(map, Templates.ERROR);
			}
			response.redirect(Routes.PLACE_SHARE + "?liberation=ok");

		} else {
			/*
			 * Annulation d'un partage
			 */
			String unshareDate = request.queryParams("unshareDate");
			if (!StringUtils.isEmpty(unshareDate)) {
				try {
					placeService.unshare(user, unshareDate);
				} catch (UnshareException e) {
					map.put("message", "Une erreur est survenue lors de l'annulation !");
					return new ModelAndView(map, Templates.ERROR);
				}
				response.redirect(ConfHelper.complementUrl() + Routes.PLACE_SHARE + "?annulation=ok");
			}

		}

		/*
		 * Liste des dates partagées
		 */
		List<Place> datesPartages = placeService.getShared(user);
		if (!datesPartages.isEmpty()) {
			map.put("datesPartages", datesPartages);
		}

		
		/*
		 * Liste des utilisateurs demandeurs de places
		 */
		List<User> usersWithoutPlaces = userService.getAllUsersWithoutPlaces();
		map.put("usersSansPlaces", usersWithoutPlaces);
		
		return new ModelAndView(map, Templates.SHARE);
	}
}
