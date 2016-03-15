package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.Messages;
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

	protected void onLogin(Request request, Response response, Map<String, Object> map) {
		
		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		
		User user = null;
		try {
			user = userService.userAuthentication(email, pwd);

			if (user != null) {
				addAuthenticatedUser(request, user);
				Routes.redirect(user, response);
			} else {
				map.put(ERROR, "Utilisateur introuvable !");
				map.put(EMAIL, email);
			}

		} catch (Exception e) {
			
			map.put(ERROR, Messages.get(e.getMessage()));	
			map.put(EMAIL, email);
			
		}
	}

	private void addAuthenticatedUser(Request request, User user) {
		request.session().attribute("user", user);
	}
}
