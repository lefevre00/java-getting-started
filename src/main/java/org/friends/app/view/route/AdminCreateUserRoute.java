package org.friends.app.view.route;

import static org.friends.app.view.RequestHelper.getAppUrl;

import java.util.Map;

import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.validator.EmailValidator;
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
					userService.create(nouvelUtilisateur,  getAppUrl(request));
				
			} catch (NumberFormatException e) {
				map.put(Routes.KEY_ERROR, Messages.get("number.invalid"));
			} catch (Exception e) {

				if (UserService.EMAIL_REQUIRED.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, Messages.get("email.required"));

				if (UserService.EMAIL_ERROR.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, Messages.get("email.error"));

				if (UserService.USER_EXISTE.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, Messages.get("admin.create.user").replaceAll("#email_user#", email));
				
				if (UserService.PLACE_ALREADY_USED.equals(e.getMessage()))
					map.put(Routes.KEY_ERROR, Messages.get("admin.place.used").replaceAll("#placeNumber#", placeNumber));
				
				map.put(EMAIL, email);
			}
			if(!map.containsKey(Routes.KEY_ERROR)) {	
				
				String message = Messages.get("admin.create.user");
				map.put(Routes.KEY_SUCCESS, message.replaceAll("#email_user#", email)); 
			}
		}
}