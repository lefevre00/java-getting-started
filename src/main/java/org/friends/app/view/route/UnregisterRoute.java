package org.friends.app.view.route;

import java.util.List;
import java.util.Map;

import org.friends.app.ConfHelper;
import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.omg.CORBA.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class UnregisterRoute extends AuthenticatedRoute {

	@Autowired
	UserService userService;

	@Override
	protected ModelAndView doHandle(Request request, Response response) {
		Map<String, Object> map = Routes.getMap(request);
		User userLogue = getUser(request);
		String email_user = null;
		try {
			if(!isAdmin(request)) {
				userService.delete(userLogue);
			} else {
				email_user = request.queryParams("email_user");
				if(email_user != null) {
					User userToDelete = userService.findUserByEmail(email_user);
					userService.delete(userToDelete);
				}
			}
		} catch (UnknownUserException | DataIntegrityException e) {
			if(!isAdmin(request)) {
				User user = getUser(request);
				map.put("user", user);
				if (user.getPlaceNumber() != null) {
					map.put("placeNumber", user.getPlaceNumber().toString());
				}
				map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
				return new ModelAndView(map, Templates.SETTING);
			} else {
				map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
				return new ModelAndView(map, Templates.ADMIN_PAGE);
			}
		}
		if(!isAdmin(request)) {
			response.redirect(ConfHelper.complementUrl() + Routes.LOGOUT);
			return new ModelAndView(map, Templates.LOGOUT);
		} else {
			String message = Messages.get("admin.delete.user");
			map.put(Routes.KEY_SUCCESS, message.replaceAll("#email_user#", email_user)); 
			List<User> allUsers = userService.getAllUsersWithoutAdmin();
			map.put("inscriptionLibre", ConfHelper.INSCRIPTION_LIBRE ? null : "oui");
			map.put("usersList", allUsers);
			return new ModelAndView(map, Templates.USERS_LIST);
		}
	}
}