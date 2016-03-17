package org.friends.app.view.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class BookedRoute extends AuthenticatedRoute {

	private PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		List<Place> reservations = service.getReservations(getUser(request));
		Map<String, Object> map = new HashMap<>();
		map.put("places", reservations);
		return new ModelAndView(map, "reservations.ftl");
	}

}
