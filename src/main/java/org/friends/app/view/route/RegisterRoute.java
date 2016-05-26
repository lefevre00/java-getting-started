package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.view.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

@Component
public class RegisterRoute implements TemplateViewRoute {
	
	private static final String ERROR = "error";
	private static final String EMAIL = "email";
	
	@Autowired
	UserServiceBean userService;
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = Routes.getMap(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onRegister(request, response, map);
		}
		
    	return new ModelAndView(map, "createUser.ftl");	
	}
	
	protected void onRegister(Request request, Response response, Map<String, Object> map) {
	
		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		String pwdConfirmation = request.queryParams("pwdConfirmation");
		String placeNumber = request.queryParams("placeNumber");
		
		try {

			userService.parametersValidator(email, pwd, pwdConfirmation);
			User user = new User(email, pwd, StringUtils.isEmpty(placeNumber) ? null : Integer.parseInt(placeNumber));
			
			User userExiste = userService.findUserByEmail(user.getEmailAMDM());
			if(userExiste == null) {
				user = userService.create(user, RequestHelper.getAppUrl(request));
				response.redirect(Routes.REGISTRED);
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
			
			if(UserService.PWD_CONFIRMATION_REQUIRED.equals(e.getMessage()))
				map.put(ERROR, "Vous devez saisir une confirmation de mot de passe !");
			
			if(UserService.PWD_UNMATCHING.equals(e.getMessage()))
				map.put(ERROR, "Le mot de passe et la confirmation doivent être identiques !");			
			
			map.put(EMAIL, email);
		}
	}
}
