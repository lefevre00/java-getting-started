package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.PlaceService;
import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.view.route.AuthenticatedRoute;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

public class SettingRoute extends AuthenticatedRoute {

	private static final String ERROR = "error";

	private static final String INFO = "info";

	UserServiceBean userService = new UserServiceBean();

	@Override
	protected ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = new HashMap<>();
		
		User user = getUser(request);

		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			Integer i = null;
			boolean doUpdate = true;
			
			String place = request.queryParams("placeNumber");
			if (StringUtils.isNotEmpty(place)) {
				try {
					i = Integer.valueOf(place);
				} catch (NumberFormatException e) {
					map.put(ERROR, Messages.get(PlaceService.INVALID_NUMBER));
					doUpdate  = false;
				}
			} 
			
			if (doUpdate) {
				user.setPlaceNumber(i);
				user = userService .update(user);
				request.session().attribute("user", user);
				map.put(INFO, Messages.get("update.ok"));
			}
		}
		
		if (user.getPlaceNumber() != null)
			map.put("placeNumber", user.getPlaceNumber().toString());
		
		return new ModelAndView(map, Templates.SETTING);
	}
}
