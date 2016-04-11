package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class BookedRoute extends AuthenticatedRoute {

	private PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		User user = getUser(request);
		List<Place> reservations = new ArrayList<Place>();
		try {
			reservations = service.getReservationsOrRelease(user);
		} catch (SQLException | URISyntaxException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = getMap();
		
		map.put("places", reservations);
		map.put("placenumber", user.getPlaceNumber() == null ? "" : user.getPlaceNumber());
		map.put("dateReservation", getDateReservation(reservations));
		map.put("presentation", user.getPlaceNumber() == null ? "Voici les places que vous avez réservées :" : "Voici les dates de libération de la place " + user.getPlaceNumber().toString() + " :");

		return new ModelAndView(map, "reservations.ftl");
	}

	
	private String getDateReservation(List<Place> reservations){
		
		String dateReservation="";
		if (reservations.isEmpty()){
			dateReservation = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else if (reservations.size() == 1 ) {
			dateReservation = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return dateReservation;
	}
}
