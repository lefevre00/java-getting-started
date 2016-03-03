package org.friends.app.view;

import org.friends.app.model.Place;
import org.friends.app.model.User;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class SharePlace implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request paramRequest, Response paramResponse)
			throws Exception {
		User user = (User) paramRequest.session().attribute("user");
		Place place = new Place(user.getPlaceNumber() != null ? user.getPlaceNumber().intValue() : null, true);
		ModelAndView model = new ModelAndView(place, "sharePlace.ftl");
		return model;
	}
	
}
