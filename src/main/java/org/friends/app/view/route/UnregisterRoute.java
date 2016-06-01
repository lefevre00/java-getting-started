package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.omg.CORBA.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class UnregisterRoute extends AuthenticatedRoute {

	@Autowired
	UserService userService;

	@Override
	protected ModelAndView doHandle(Request request, Response response) {
		try {
			userService.delete(getUser(request));
		} catch (UnknownUserException | DataIntegrityException e) {
			Map<String, Object> map = Routes.getMap(request);
			User user = getUser(request);
			if (user.getPlaceNumber() != null) {
				map.put("placeNumber", user.getPlaceNumber().toString());
			}
			map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
			return new ModelAndView(map, Templates.SETTING);
		}

		response.redirect(Routes.LOGOUT);
		return new ModelAndView(null, Templates.LOGOUT);
	}
}