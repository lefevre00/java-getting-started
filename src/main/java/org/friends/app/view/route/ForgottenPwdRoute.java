package org.friends.app.view.route;

import static org.friends.app.view.RequestHelper.getAppUrl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.friends.app.Messages;
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
			try {
				onLogin(request, response, map);
			} catch (ValidationException e) {
				map.put("error", Messages.get(e.getMessage()));
			}
		}

		return new ModelAndView(map, "pwd_lost.ftl");
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) throws Exception {

		String email = request.queryParams("email");

		// Email validator
		if (!UserServiceBean.emailAMDMValidator(email))
			throw new ValidationException("L'email saisi est incorrect !");

		userService.resetPassword(email, getAppUrl(request));
		response.redirect(Routes.PASSWORD_RESET);
	}
}
