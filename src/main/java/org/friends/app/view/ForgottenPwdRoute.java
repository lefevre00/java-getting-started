package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class ForgottenPwdRoute implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onLogin(request, response, map);
		}
		
    	return new ModelAndView(map, "lostPwd.ftl");
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) {
		
	}

}
