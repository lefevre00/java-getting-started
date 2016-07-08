package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import spark.utils.StringUtils;

@Component
public class StatisticsRoute extends AuthenticatedRoute {

	public final static String SHARED_PLACES_TITLE = "Liste des places partagées";
	public final static String OCCUPIED_PLACES_TITLE = "Liste des places occupées";
	public final static String EMPTY_PLACES_TITLE = "Liste des places inoccupées";
	
	@Autowired
	private DateService dateService;
	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);

		User user = request.session().attribute("user");

		
		if (!"true".equalsIgnoreCase((String) map.get("admin")) &&
				(user==null || user.getId()== null && StringUtils.isEmpty(user.getEmailAMDM().trim())) ) {
			response.redirect(Routes.ACCESS_DENIED);
		}
		else {
			if ("POST".equalsIgnoreCase(request.requestMethod())) {
				LocalDate dateDebut = request.queryParams("dateDebut") != null
						? DateUtil.stringToDate(request.queryParams("dateDebut"), Locale.FRANCE) : null;
				LocalDate dateFin = request.queryParams("dateFin") != null
						? DateUtil.stringToDate(request.queryParams("dateFin"), Locale.FRANCE) : null;
						
				List<Place> listePlaces = placeService.getAllPlaceBetweenTwoDates(DateUtil.dateToString(dateDebut), DateUtil.dateToString(dateFin));
				if (!listePlaces.isEmpty()) {
					
					int nbPlaceOccupe = 0;
					int nbPlaceInoccupe = 0;
					for (Place place : listePlaces){
						if (StringUtils.isEmpty(place.getUsedBy().trim())){
							nbPlaceInoccupe++;
						}
						else{
							nbPlaceOccupe++;
						}
					}
					map.put("dateDebut", DateUtil.dateToString(dateDebut));
					map.put("dateFin", DateUtil.dateToString(dateFin));
					map.put("nbrePartage", nbPlaceOccupe+ nbPlaceInoccupe);
					map.put("nbreOccupe", nbPlaceOccupe);
					map.put("nbreInoccupe", nbPlaceInoccupe);
				}
				
			}
			else{
				// Accès Administration
				if ( "true".equalsIgnoreCase((String) map.get("admin")) ) {
					String var = request.queryParams("var");
					String dateDebut = request.queryParams("dd");
					String dateFin = request.queryParams("df");
					
					if (StringUtils.isNotEmpty(var)) {
						List<Place> listePlaces = placeService.getAllPlaceBetweenTwoDates(dateDebut, dateFin);
						List<Place> listeFinale = new ArrayList<Place>();
						if ("i".equals(var)){
							map.put("title", EMPTY_PLACES_TITLE);
							for (Place place : listePlaces) {
								if (StringUtils.isEmpty(place.getUsedBy().trim())){
									listeFinale.add(place);
								}
							}
						}
						else if ("o".equals(var)){
							map.put("title", OCCUPIED_PLACES_TITLE);
							for (Place place : listePlaces) {
								if (!StringUtils.isEmpty(place.getUsedBy().trim())){
									listeFinale.add(place);
								}
							}
						}
						else{
							map.put("title", SHARED_PLACES_TITLE);
							listeFinale = listePlaces;
						}
						
						map.put("listePlaces", listeFinale);
						
						return new ModelAndView(map, Templates.STATS_DETAIL);
					}
				}
				// Accès user
				else {
					String var = request.queryParams("var");
					String email = request.queryParams("email");
					String placeNumber = request.queryParams("place");
					List<Place> listePlaces = new ArrayList<Place>();
					if (StringUtils.isNotEmpty(var) && StringUtils.isNotEmpty(email)) {
						if (placeNumber != null){
							Integer numPlace = Integer.valueOf(placeNumber);
							listePlaces = placeService.getAllSharedDatesByUser(numPlace);
							map.put("title", SHARED_PLACES_TITLE);
						}
						else{
							listePlaces = placeService.getAllPlacesBookedByUser(email);
							map.put("title", OCCUPIED_PLACES_TITLE);
						}
						map.put("listePlaces", listePlaces);
						
						return new ModelAndView(map, Templates.STATS_DETAIL);						
					}
				}

			}
		}
		return new ModelAndView(map, Templates.STATISTICS);

	}
}
