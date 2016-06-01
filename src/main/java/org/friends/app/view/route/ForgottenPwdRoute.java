package org.friends.app.view.route;

import static org.friends.app.view.RequestHelper.getAppUrl;

import java.util.Map;

import javax.xml.bind.ValidationException;

import org.friends.app.Messages;
import org.friends.app.service.UserService;
import org.friends.app.validator.EmailValidator;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

@Component
public class ForgottenPwdRoute implements TemplateViewRoute {

	@Autowired
	UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = Routes.getMap(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			try {
				onLogin(request, response, map);
			} catch (ValidationException e) {
				map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
			}
		}

		return new ModelAndView(map, Templates.PASSWORD_LOST);
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) throws Exception {

		String email = request.queryParams("email");

		// Email validator
		if (!EmailValidator.isValid(email))
			throw new ValidationException("L'email saisi est incorrect !");

		userService.resetPassword(email, getAppUrl(request));
		response.redirect(Routes.PASSWORD_RESET);
	}
}
