package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.security.AccessControlException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.friends.app.Configuration;
import org.friends.app.dao.PlaceDao;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;

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
		return dateRecherche.format(PlaceDao.formatter);
	}
	
	protected String rechercherLejourPrecedent(LocalDate dateRecherche) {
		if(DayOfWeek.MONDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.minusDays(3);
		}else{
			dateRecherche = dateRecherche.minusDays(1);
		}
		return dateRecherche.compareTo(LocalDate.now()) <0 ? null : dateRecherche.format(PlaceDao.formatter);
	}
	/**
	 * Retourne la prochaine date de réservation
	 * @return
	 */
	protected String rechercheLaProchaineDateUtilisable(){
		LocalDate dateUtilisable = LocalDate.now();
		if(DayOfWeek.SUNDAY.equals(dateUtilisable.getDayOfWeek())){
			dateUtilisable = dateUtilisable.plusDays(3);
		}else if(DayOfWeek.SATURDAY.equals(dateUtilisable.getDayOfWeek())){
			dateUtilisable = dateUtilisable.plusDays(2);
		}
		return dateUtilisable.format(PlaceDao.formatter);
		
	}
}
