package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class UserListRoute extends AuthenticatedRoute {

	@Autowired
	private UserService userService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);


		if (!"true".equalsIgnoreCase((String) map.get("admin"))) {
			throw new RuntimeException("Access denied");
		}
		
		map.put("listUsers", userService.getAllUser());

		
		return new ModelAndView(map, Templates.LISTUSERS);
	}
}
