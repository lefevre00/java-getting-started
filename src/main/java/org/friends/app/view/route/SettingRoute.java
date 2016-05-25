package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class SettingRoute extends AuthenticatedRoute {

	private static final String ERROR = "error";

	private static final String INFO = "info";

	@Autowired
	UserService userService;

	@Override
	protected ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);
		User user = getUser(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			boolean doUpdate = true;

			String placeParam = request.queryParams("placeNumber");
			Integer place = null;
			if (StringUtils.isNotEmpty(placeParam)) {
				try {
					place = Integer.valueOf(placeParam);
				} catch (NumberFormatException e) {
					map.put(ERROR, Messages.get(PlaceService.INVALID_NUMBER));
					doUpdate = false;
				}
			}

			if (doUpdate) {
				boolean updated = false;
				try {
					user = userService.changePlace(user, place);
					updated = true;
				} catch (DataIntegrityException e) {
					map.put(ERROR, Messages.get(e.getMessage()));
				}

				if (updated) {
					request.session().attribute("user", user);
					map.put(INFO, Messages.get("update.ok"));
				}
			}
		}

		if (user.getPlaceNumber() != null)
			map.put("placeNumber", user.getPlaceNumber().toString());

		return new ModelAndView(map, Templates.SETTING);
	}
}
