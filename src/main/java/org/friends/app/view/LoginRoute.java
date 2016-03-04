package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.model.User;
import org.friends.app.service.UserService;
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
				String dest = "/protected/search";
				if (user.getPlaceNumber() != null)
					dest = "/"; //"/protected/sharePlace"
				response.redirect(dest);
			}
			map.put(ERROR, "Utilisateur introuvable !");
			map.put(EMAIL, email);
			

		} catch (Exception e) {
			
			if(UserService.EMAIL_REQUIRED.equals(e.getMessage()))
				map.put(ERROR, "Vous devez saisir une adresse email !");	
			
			if(UserService.PWD_REQUIRED.equals(e.getMessage()))
				map.put(ERROR, "Vous devez saisir un mot de passe !");	
			
			if (UserService.EMAIL_ERROR.equals(e.getMessage()))
				map.put(ERROR, "L'email saisi est invalide !");
			
			if(UserService.PWD_ERROR.equals(e.getMessage()))
				map.put(ERROR, "Le mot de passe saisi est incorrect !");

			map.put(EMAIL, email);
			
			
		}
	}
	
	private void addAuthenticatedUser(Request request, User user) {
		request.session().attribute("user", user);
	}

}
