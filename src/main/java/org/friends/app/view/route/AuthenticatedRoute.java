package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.security.AccessControlException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.friends.app.Configuration;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

public abstract class AuthenticatedRoute implements TemplateViewRoute {

	UserServiceBean userService = new UserServiceBean();
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		checkAuthenticated(request, response);
		return doHandle(request, response);
	}

	protected abstract ModelAndView doHandle(Request request, Response response);

	private void checkAuthenticated(Request request, Response response) throws SQLException, URISyntaxException {
		User user = getUser(request);
		
		// 1 : try to find user in session
		if (user != null)
			return;
		
		// 2. Try to find user using cookie
		boolean userFound = false;
		String cookie = request.cookie(Configuration.COOKIE);
		if (!StringUtils.isEmpty(cookie)) {
			user = userService.findUserByCookie(cookie);
			if (user != null) {
				request.session().attribute("user", user);
				userFound = true;
			} else {
				// Clean cookie if no user
				response.removeCookie(Configuration.COOKIE);
			}
		}

		// Stop route
		if (!userFound) {
			throw new AccessControlException("User not authenticated");
		}
	}

	protected User getUser(Request request) {
		return request.session().attribute("user");
	}
	
	protected String rechercherLejourSuivant(LocalDate dateRecherche) {
		if(DayOfWeek.FRIDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.plusDays(3);
		}else if(DayOfWeek.SATURDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.plusDays(2);
		}else{
			dateRecherche = dateRecherche.plusDays(1);
		}
		return DateUtil.dateToString(dateRecherche);
	}
	
	/**
	 * Build the default map object to tell the template that the user is logged in.
	 * @return
	 */
	protected Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("logged", "true");
		return map ;
	}
	
	
	
	protected String getDateReservation(List<Place> reservations){
		
		String dateReservation="";
		if (reservations.isEmpty()){
			dateReservation = DateUtil.dateToString(LocalDate.now());
		}
		else if (reservations.size() == 1 ) {
			
			if (DayOfWeek.FRIDAY.equals(LocalDate.now().getDayOfWeek())){
				dateReservation = DateUtil.dateToString(LocalDate.now().plusDays(3));
			}
			else if (DayOfWeek.SATURDAY.equals(LocalDate.now().getDayOfWeek())){
				dateReservation = DateUtil.dateToString(LocalDate.now().plusDays(2));
			}
			else{
				dateReservation = DateUtil.dateToString(LocalDate.now().plusDays(1));
			}
			
		}
		return dateReservation;
	}
}
