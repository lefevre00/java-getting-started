package org.friends.app.view.route;

import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;

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
		if (!reservations.isEmpty()){
			map.put("places", reservations);
		}
		map.put("placeHolder", user.getPlaceNumber()==null ? null : true);
		map.put("placenumber", user.getPlaceNumber() == null ? "" : user.getPlaceNumber());
		map.put("dateReservation", getDateReservation(reservations));
//		map.put("presentation", user.getPlaceNumber() == null ? "Voici les places que vous avez réservées :" : "Voici les dates de libération de la place " + user.getPlaceNumber().toString() + " :");

		return new ModelAndView(map, "reservations.ftl");
	}

}
