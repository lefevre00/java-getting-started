package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

/**
 * Page listing the booked places for days starting today.
 * Give access to booking page.
 * @author michael
 *
 */
public class BookedRoute extends AuthenticatedRoute {

	private static final String URL_BASE = "/protected/booked";
	private PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		User user = getUser(request);
		
		String release = request.queryParams("release");
		if (StringUtils.isNotEmpty(release)) {
			service.release(user, release);
		}
		
		// on récupère les réservations pour des dates égales/postérieures à la date du jour  
		List<Place> reservations = service.getReservations(user);
		
		Map<String, Object> map = getMap();
		map.put("urlBase", URL_BASE);
		if (!reservations.isEmpty()){
			map.put("places", reservations);
		}
		map.put("placeHolder", user.getPlaceNumber()==null ? null : true);
		map.put("placenumber", user.getPlaceNumber() == null ? "" : user.getPlaceNumber());
		if (user.getPlaceNumber()==null){
			map.put("dateReservation", getDateReservation(reservations));
		}
		else{
			map.put("dateReservation", getDateReservation4UserPlaceHolder(reservations, user.getPlaceNumber()));
		}
		map.put("presentation", reservations.isEmpty() ? "Aucune réservation enregistrée." : "Voici les places que vous avez réservées :");
		return new ModelAndView(map, "reservations.ftl");
	}

	protected String getDateReservation(List<Place> reservations){
		
		String dateReservation="";
		if (reservations.isEmpty()){
			dateReservation = DateUtil.dateToString(LocalDate.now());
		}
		else if (reservations.size() == 1 ) {
			rechercherLejourSuivant(LocalDate.now());
		}
		return dateReservation;
	}
	
	/*
	 * Liste reservations contient au maximum 2 dates de réservations
	 * Si 2 réservations, pas de nouvelles réservations possibles 
	 */
	public String getDateReservation4UserPlaceHolder(List<Place> reservations, Integer placeNumber) {
		
		String retour = "";
		if (reservations.isEmpty()){
			// on vérifie si place est partagée pour j
			retour = isPlaceSharedAndOccupiedAtDate(placeNumber, DateUtil.dateToString(LocalDate.now()));
		}
		else if (reservations.size() == 1){
			// on vérifie si place est partagée pour j+1
			retour = isPlaceSharedAndOccupiedAtDate(placeNumber, rechercherLejourSuivant(LocalDate.now().plusDays(1)));
		}
		return retour;
	}	
	
	private String isPlaceSharedAndOccupiedAtDate(Integer placeNumber, String date){
		
		Place place = service.isPlaceShared(placeNumber, date);					
		if (place!=null && StringUtils.isNotEmpty(place.getUsedBy())){
			return place.getOccupationDate();
		}
		return "";
	}	
	
}
