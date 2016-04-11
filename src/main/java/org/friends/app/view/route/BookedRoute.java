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

/**
 * Page listing the booked places for days starting today.
 * Give access to booking page.
 * @author michael
 *
 */
public class BookedRoute extends AuthenticatedRoute {

	private PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		User user = getUser(request);
		List<Place> reservations = service.getReservationsOrRelease(user);
		
		Map<String, Object> map = getMap();
		map.put("places", reservations);
		map.put("dateReservation", getDateReservation(reservations));
		map.put("presentation", user.getPlaceNumber() == null ? "Voici les places que vous avez réservées :" : "Voici les dates de libération de la place " + user.getPlaceNumber().toString() + " :");

		return new ModelAndView(map, "reservations.ftl");
	}

	
	private String getDateReservation(List<Place> reservations){
		
		String dateReservation="";
		if (reservations.isEmpty()){
			dateReservation = DateUtil.dateAsString(LocalDate.now());
		}
		else if (reservations.size() == 1 ) {
			dateReservation = DateUtil.dateAsString(LocalDate.now().plusDays(1));
		}
		return dateReservation;
	}
}
