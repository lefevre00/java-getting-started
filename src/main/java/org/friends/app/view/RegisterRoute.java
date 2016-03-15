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
import spark.utils.StringUtils;

public class RegisterRoute implements TemplateViewRoute {
	
	private static final String ERROR = "error";
	private static final String EMAIL = "email";
	
	UserServiceBean userService = new UserServiceBean();
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = new HashMap<>();
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onRegister(request, response, map);
		}
		
    	return new ModelAndView(map, "createUser.ftl");	
	}
	
	protected void onRegister(Request request, Response response, Map<String, Object> map) {
	
		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		String placeNumber = request.queryParams("placeNumber");
		
		try {

			userService.parametersValidator(email, pwd);
			User user = new User(email, pwd, StringUtils.isEmpty(placeNumber)? 0 : Integer.parseInt(placeNumber));
			
			User userExiste = userService.findUserByEmail(user.getEmailAMDM());
			if(userExiste == null) {
				user = userService.create(user, getAppUrl(request));
				request.session().attribute("user", user);
				Routes.redirect(user, response);

			} else {
				map.put(ERROR, "Un compte existe déjà avec cette adresse email !");
			}
			map.put(EMAIL, email);
				
		} catch (NumberFormatException e) {
			map.put(ERROR, "Le numéro de place est un numéro !");
		} catch (Exception e) {

			if(UserService.EMAIL_REQUIRED.equals(e.getMessage()))
				map.put(ERROR, "Vous devez saisir une adresse email !");			
			
			if (UserService.EMAIL_ERROR.equals(e.getMessage()))
				map.put(ERROR, "L'email saisi est invalide !");
			
			if(UserService.PWD_REQUIRED.equals(e.getMessage()))
				map.put(ERROR, "Vous devez saisir un mot de passe !");			
			
			map.put(EMAIL, email);
		}
	}

	private String getAppUrl(Request req) {
		String url = req.url();
		String back = url.substring(0, url.length() - req.uri().length());
		return back;
	}
}
