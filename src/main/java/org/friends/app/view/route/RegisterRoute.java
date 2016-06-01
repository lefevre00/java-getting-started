package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.validator.EmailValidator;
import org.friends.app.view.RequestHelper;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

@Component
public class RegisterRoute implements TemplateViewRoute {

	private static final String EMAIL = "email";

	@Autowired
	UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = Routes.getMap(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onRegister(request, response, map);
		}

		return new ModelAndView(map, Templates.REGISTER);
	}

	protected void onRegister(Request request, Response response, Map<String, Object> map) {

		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		String placeNumber = request.queryParams("placeNumber");

		try {
			if (Strings.isNullOrEmpty(email))
				throw new Exception(UserService.EMAIL_REQUIRED);

			if (!EmailValidator.isValid(email))
				throw new Exception(UserService.EMAIL_ERROR);

			if (Strings.isNullOrEmpty(pwd))
				throw new Exception(UserService.PWD_REQUIRED);

			User user = new User(email, pwd, StringUtils.isEmpty(placeNumber) ? null : Integer.parseInt(placeNumber));

			User userExiste = userService.findUserByEmail(user.getEmailAMDM());
			if (userExiste == null) {
				user = userService.create(user, RequestHelper.getAppUrl(request));
				response.redirect(Routes.REGISTRED);
			} else {
				map.put(Routes.KEY_ERROR, "Un compte existe déjà avec cette adresse email !");
			}
			map.put(EMAIL, email);

		} catch (NumberFormatException e) {
			map.put(Routes.KEY_ERROR, "Le numéro de place est un numéro !");
		} catch (Exception e) {

			if (UserService.EMAIL_REQUIRED.equals(e.getMessage()))
				map.put(Routes.KEY_ERROR, "Vous devez saisir une adresse email !");

			if (UserService.EMAIL_ERROR.equals(e.getMessage()))
				map.put(Routes.KEY_ERROR, "L'email saisi est invalide !");

			if (UserService.PWD_REQUIRED.equals(e.getMessage()))
				map.put(Routes.KEY_ERROR, "Vous devez saisir un mot de passe !");

			map.put(EMAIL, email);
		}
	}
}
