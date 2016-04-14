package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

public class ShareRoute extends AuthenticatedRoute {
	
	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		Map<String, Object> map = getMap();
    	User user = getUser(request);		
    	
		if (user.getPlaceNumber() == null){
			throw new RuntimeException("A user without place cannot share a place");
		}
    	
		// Permet d'identifier l'utilisateur avec une place attribuée
		map.put("placeHolder", true);
		map.put("placeNumber", user.getPlaceNumber());
		
		
		/*
		 * Annulation d'un partage
		 */
		String unshareDate = request.queryParams("unshareDate");
		if (!StringUtils.isEmpty(unshareDate)){
			try {
				placeService.unsharePlaceByDate(user, unshareDate);
			} catch (Exception e) {
				map.put("message", "Une erreur est survenue lors de l'annulation !"); 
		        return new ModelAndView(map, "error.ftl");	
			}
		}
		
		
		/*
		 * Partage de places
		 */
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			
			LocalDate dateDebut = request.queryParams("dateDebut") != null ? DateUtil.stringToDate(request.queryParams("dateDebut"), Locale.FRANCE) : null;
			LocalDate dateFin = request.queryParams("dateFin") != null ? DateUtil.stringToDate(request.queryParams("dateFin"), Locale.FRANCE) : null;

			boolean retour=false;
			try {
				retour = placeService.sharePlaces(user, dateDebut, dateFin);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "Une erreur est survenue lors de l'enregistrement de données !"); 
		        return new ModelAndView(map, "error.ftl");
			}
			if (!retour){
				if (dateDebut.equals(dateFin)){
					map.put("message", "Vous avez déjà partagé votre place pour le " + DateUtil.dateToString(dateDebut, Locale.FRANCE));	
				}
				else{
					map.put("message", "Vous avez déjà partagé ces dates !");
				}
		        return new ModelAndView(map, "error.ftl");	
			}

		} 


		/*
		 * Liste des dates partagées
		 */
		List<Place> datesPartages = placeService.getReservationsOrRelease(user);
		if (!datesPartages.isEmpty()){
			map.put("datesPartages", datesPartages);
		}


		return new ModelAndView(map, "sharePlace.ftl");
	}
	

	public String nextDayWithOutWeekEnd(){
		String retour = DateUtil.dateToString(LocalDate.now());
		return retour;
	}
}

