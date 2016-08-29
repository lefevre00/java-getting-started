package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.ArrayList;
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
import spark.utils.StringUtils;

@Component
public class StatisticsRoute extends AuthenticatedRoute {

	private static final String DATE_FIN = "dateFin";
	private static final String DATE_DEBUT = "dateDebut";
	public final static String SHARED_PLACES_TITLE = "Liste des places partagées";
	public final static String OCCUPIED_PLACES_TITLE = "Liste des places occupées";
	public final static String EMPTY_PLACES_TITLE = "Liste des places inoccupées";

	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);

		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			String paramDebut = request.queryParams(DATE_DEBUT);
			LocalDate dateDebut = paramDebut != null ? DateUtil.stringToDate(paramDebut, Locale.FRANCE) : null;
			String paramFin = request.queryParams(DATE_FIN);
			LocalDate dateFin = paramFin != null ? DateUtil.stringToDate(paramFin, Locale.FRANCE) : null;

			List<Place> listePlaces = placeService.getAllPlaceBetweenTwoDates(DateUtil.dateToString(dateDebut),
					DateUtil.dateToString(dateFin));

			if (!listePlaces.isEmpty()) {
				int nbPlaceOccupe = 0;
				int nbPlaceInoccupe = 0;
				for (Place place : listePlaces) {
					if (StringUtils.isEmpty(place.getUsedBy().trim())) {
						nbPlaceInoccupe++;
					} else {
						nbPlaceOccupe++;
					}
				}
				map.put(DATE_DEBUT, DateUtil.dateToString(dateDebut));
				map.put(DATE_FIN, DateUtil.dateToString(dateFin));
				map.put("nbrePartage", nbPlaceOccupe + nbPlaceInoccupe);
				map.put("nbreOccupe", nbPlaceOccupe);
				map.put("nbreInoccupe", nbPlaceInoccupe);
			}

		} else {
			String var = request.queryParams("var");

			// Accès Administration
			if (isAdmin(request)) {
				String dateDebut = request.queryParams("dd");
				String dateFin = request.queryParams("df");

				if (StringUtils.isNotEmpty(var)) {
					List<Place> listePlaces = placeService.getAllPlaceBetweenTwoDates(dateDebut, dateFin);
					List<Place> listeFinale = new ArrayList<Place>();
					if ("i".equals(var)) {
						map.put("title", EMPTY_PLACES_TITLE);
						for (Place place : listePlaces) {
							if (StringUtils.isEmpty(place.getUsedBy().trim())) {
								listeFinale.add(place);
							}
						}
					} else if ("o".equals(var)) {
						map.put("title", OCCUPIED_PLACES_TITLE);
						for (Place place : listePlaces) {
							if (!StringUtils.isEmpty(place.getUsedBy().trim())) {
								listeFinale.add(place);
							}
						}
					} else {
						map.put("title", SHARED_PLACES_TITLE);
						listeFinale = listePlaces;
					}

					map.put("listePlaces", listeFinale);

					return new ModelAndView(map, Templates.STATS_DETAIL);
				}
			}
			// Accès user
			else {
				String email = request.queryParams("email");
				String placeNumber = request.queryParams("place");
				List<Place> listePlaces = new ArrayList<Place>();
				if (StringUtils.isNotEmpty(var) && StringUtils.isNotEmpty(email)) {
					// Historiques des places partagées
					if (StringUtils.isNotEmpty(placeNumber)) {
						Integer numPlace = Integer.valueOf(placeNumber);
						listePlaces = placeService.getAllSharedDatesByUser(numPlace);
						map.put("title", SHARED_PLACES_TITLE);
					} 
					// Historique des places réservées
					else {
						listePlaces = placeService.getAllPlacesBookedByUser(email);
						map.put("title", OCCUPIED_PLACES_TITLE);
					}
					map.put("listePlaces", listePlaces);

					return new ModelAndView(map, Templates.STATS_DETAIL);
				}
			}
		}
		return new ModelAndView(map, Templates.STATISTICS);

	}
}
