package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
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
		List<Place> reservations = new ArrayList<Place>();
		try {
			reservations = service.getReservations(getUser(request));
		} catch (SQLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("places", reservations);
		return new ModelAndView(map, "reservations.ftl");
	}

}
