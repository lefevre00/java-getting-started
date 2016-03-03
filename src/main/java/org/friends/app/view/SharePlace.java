package org.friends.app.view;

import java.util.ArrayList;
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
						System.out.println(paramRequest.queryParams("dateFin"));
						placeService.releasePlace(user.getPlaceNumber().intValue(), paramRequest.queryParams("dateDebut"));
				        paramResponse.redirect("/protected/search");

				        map.put("places", new ArrayList<>());
				        model = new ModelAndView(map, "search.ftl");
					}
			}else{
				Place place = new Place(user.getPlaceNumber() != null ? user.getPlaceNumber().intValue() : null, true);
				model = new ModelAndView(place, "sharePlace.ftl");
			}
		}
		return model;
	}
	
}
