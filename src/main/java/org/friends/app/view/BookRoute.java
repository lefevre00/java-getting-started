package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class BookRoute extends AuthenticatedRoute {

	PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("dateRecherche", "test");
		map.put("message", "TODO");
		
		return new ModelAndView(map , "book.ftl");
	}
}
