package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

import org.friends.app.ConfHelper;
import org.friends.app.model.User;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.friends.app.util.DateUtil;
import org.friends.app.validator.EmailValidator;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class AdminShareRoute extends AdminAuthRoute {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PlaceService placeService;	
	
	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		Map<String, Object> map = Routes.getMap(request);
		
		User selectUser = null;
		if (StringUtils.isNotEmpty(request.queryParams("email"))){
			selectUser = userService.findUserByEmail(request.queryParams("email"));
			map.put("email", selectUser.getEmailAMDM());
			map.put("place", selectUser.getPlaceNumber());
		}
		
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			
			LocalDate dateDebut = request.queryParams("dateDebut") != null
					? DateUtil.stringToDate(request.queryParams("dateDebut"), Locale.FRANCE) : null;
			LocalDate dateFin = request.queryParams("dateFin") != null
					? DateUtil.stringToDate(request.queryParams("dateFin"), Locale.FRANCE) : null;
			
			// Email de l'occupant de la place
			String emailOccupant = request.queryParams("emailOccupant");
			
			// Attribution de la place
			if (StringUtils.isNotEmpty(emailOccupant)){

				// Email validator
				if (!EmailValidator.isValid(emailOccupant)){
					map.put("message", "L'email saisi est incorrect !");
					return new ModelAndView(map, Templates.ERROR);
				}
				else {
					// On vérifie que l'utilisateur occupant existe bien en base
					User userOccupant = userService.findUserByEmail(emailOccupant);
					if(userOccupant == null) {
						map.put("message", "L'utilisateur n'est pas connu !");
						return new ModelAndView(map, Templates.ERROR);
					}
				}
			}
			
			try {
				placeService.sharePlaces(selectUser, dateDebut, dateFin, emailOccupant);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "Une erreur est survenue lors de l'enregistrement de données !");
				return new ModelAndView(map, Templates.ERROR);
			}			
			response.redirect(ConfHelper.complementUrl() + Routes.USERS_LIST);
			
		}
		return new ModelAndView(map, Templates.ADMIN_SHARE);
		
	}
}