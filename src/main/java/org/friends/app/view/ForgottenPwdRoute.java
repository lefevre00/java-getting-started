package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.service.impl.UserServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class ForgottenPwdRoute implements TemplateViewRoute {
	
	UserServiceBean userService = new UserServiceBean();
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onLogin(request, response, map);
		}
		
    	return new ModelAndView(map, "lostPwd.ftl");
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) throws Exception {
	
		String email = request.queryParams("email");
		
		// Email validator
		if (!UserServiceBean.emailAMDMValidator(email))
			throw new Exception("L'email saisi est incorrect !");
		
	}
	
	
	
	
	

}
