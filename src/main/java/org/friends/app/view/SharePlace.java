package org.friends.app.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class SharePlace implements TemplateViewRoute {
	
	private PlaceServiceBean placeService = new PlaceServiceBean();

	@Override
	public ModelAndView handle(Request paramRequest, Response paramResponse)
			throws Exception {

		ModelAndView model = null;
		Map<String, Object> map = new HashMap<>();
		User user = (User) paramRequest.session().attribute("user");
		if(user==null){
			paramResponse.redirect("/");
			
			model = new ModelAndView(map, "login.ftl");
		}else{
			if ("POST".equalsIgnoreCase(paramRequest.requestMethod())) {
					if(paramRequest.queryParams("dateDebut") != null){
						placeService.releasePlace(user.getPlaceNumber().intValue(), paramRequest.queryParams("dateDebut"));
						map.put("numberPlace", paramRequest.queryParams("number"));
						map.put("places", getPlaces());
				        model = new ModelAndView(map, "search.ftl");
				        paramResponse.redirect("/protected/search");
					}
			}else{
				Place place = new Place(user.getPlaceNumber() != null ? user.getPlaceNumber().intValue() : null, true);
				model = new ModelAndView(place, "sharePlace.ftl");
			}
		}
		
		return model;
	}
	
	private List<Place> getPlaces() throws ParseException {
		List<Place> places = new ArrayList<>();
		List<Integer> freePlaces = placeService.getAvailableByDate(new Date());
		for (int i = 1; i<150; i++) {
			places.add(new Place(i, freePlaces.contains(i)));
		}
		return places;
	}
	
}
