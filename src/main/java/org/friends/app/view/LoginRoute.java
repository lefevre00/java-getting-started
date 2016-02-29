package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.Constants;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Page de Login
 * @author michael lefevre
 */
public class LoginRoute implements TemplateViewRoute {

	private static final String ERROR = "error";
	private static final String EMAIL = "email";
	
	UserServiceBean userService = new UserServiceBean();
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onLogin(request, response, map);
		}
		
    	return new ModelAndView(map, "login.ftl");
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) throws Exception {
		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		
		User user = userService.Authenticate(email, pwd);
		if (user != null) {
			response.cookie(Constants.COOKIE, user.createCookie());

			String dest = "/protected/search";
			if (user.getPlaceNumber() != null)
				dest = "/protected/sharePlace";
			response.redirect(dest);
		} else {
			map.put(ERROR, "Email ou mot de passe incorrect");
			map.put(EMAIL, email);
		}
	}
}
