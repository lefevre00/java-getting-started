package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.view.Templates;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class AdminShareRoute extends AdminAuthRoute {

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		Map<String, Object> map = Routes.getMap(request);
		
		// Passer l'utilisateur en paramètre
		
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			
			
			
		}
		return new ModelAndView(map, Templates.ADMIN_SHARE);
		
	}
}