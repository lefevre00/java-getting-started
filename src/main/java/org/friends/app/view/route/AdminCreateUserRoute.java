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
import spark.utils.StringUtils;

@Component
public class AdminCreateUserRoute extends AdminAuthRoute {

	private static final String EMAIL = "email";
	
	@Autowired
	private UserService userService;

	
	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		
		Map<String, Object> map = Routes.getMap(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onRegister(request, response, map);
		}

		return new ModelAndView(map, Templates.ADMIN_CREATE);
		
	}
		protected void onRegister(Request request, Response response, Map<String, Object> map) {

			String email = request.queryParams("email");
			String placeNumber = request.queryParams("placeNumber");

			try {
				if (Strings.isNullOrEmpty(email))
					throw new Exception(UserService.EMAIL_REQUIRED);
				
				if (!EmailValidator.isValid(email))
					throw new Exception(UserService.EMAIL_ERROR);
				
				User userExiste = userService.findUserByEmail(email);
				if (userExiste != null)
					throw new Exception(UserService.USER_EXISTE);
				
				
					User nouvelUtilisateur = new User(email, "1", StringUtils.isEmpty(placeNumber) ? null : Integer.parseInt(placeNumber));
					userService.create(nouvelUtilisateur,  RequestHelper.getAppUrl(request));
				
			} catch (NumberFormatException e) {
				map.put(Routes.KEY_ERROR, "Le numéro de place est un numéro !");
			} catch (Exception e) {

				if (UserService.EMAIL_REQUIRED.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, "Vous devez saisir une adresse email !");

				if (UserService.EMAIL_ERROR.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, "L'email saisi est invalide !");

				if (UserService.USER_EXISTE.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, "L'utilisateur "+ email + " existe déjà ! ");
				
				if (UserService.PLACE_ALREADY_USED.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, "La place  " + placeNumber + " est déjà attribuée !");

				map.put(EMAIL, email);
			}
			if(!map.containsKey(Routes.KEY_ERROR))	map.put(Routes.KEY_SUCCESS, "L'utilisateur "+ email + " a été créé ! ");
		}
}