package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.model.User;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class UserEditRoute extends AuthenticatedRoute {

	public final static String PARAM_USER = ":user_id";
	
	@Autowired
	private UserService userService;

	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		Map<String, String> params = request.params();
		Map<String, Object> map = Routes.getMap(request);
		
		String email = ((User)request.session().attribute("user")).getEmailAMDM();
		 
		if ( !"true".equalsIgnoreCase((String) map.get("admin")) &&
				(StringUtils.isEmpty(email) || !email.endsWith("@amdm.fr")) ) {
			response.redirect(Routes.ACCESS_DENIED);
		}
		
		
		/*
		 * Utilisateur
		 */
		String userId = params.get(PARAM_USER);
		
		if (StringUtils.isEmpty(userId)) {
			map.put("user", "N/C");
			map.put("message", "Utilisateur introuvable !");
		} else {
			
			map.put("userid", userId);
			User user = userService.findUserByEmail(userId);
			map.put("message", "");
			map.put("user", user);		
		}
		
		
		return new ModelAndView(map, Templates.USER_EDIT);

	}
}
