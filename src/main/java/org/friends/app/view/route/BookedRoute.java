package org.friends.app.view.route;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

/**
 * Page listing the booked places for days starting today.
 * Give access to booking page.
 * @author michael
 *
 */
public class BookedRoute extends AuthenticatedRoute {

	private PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		User user = getUser(request);
		
		String release = request.queryParams("release");
		if (StringUtils.isNotEmpty(release)) {
			service.release(user, release);
		}
		
		// on récupère les réservations pour des dates égales/postérieures à la date du jour  
		List<Place> reservations = service.getReservations(user);
		
		Map<String, Object> map = Routes.getMap(request);
		if (!reservations.isEmpty()){
			map.put("places", reservations);
		}
		
		LocalDate jourRecherche = LocalDate.now();
		map.put("dateDuJour", DateUtil.dateToFullString(LocalDate.now()));
		if(LocalDateTime.now().getHour()>14 || DateUtil.isWeekEnd(jourRecherche)){
			jourRecherche = DateUtil.rechercherDateLejourSuivant(jourRecherche);
		}
		String day = DateUtil.dateToString(jourRecherche);
		map.put("libelleShowToday", "Réserver le " + DateUtil.dateToFullString(jourRecherche));
		if (service.canBook(user, day)) {
			map.put("showToday", day);
		}
		day = DateUtil.rechercherStrLejourSuivant(jourRecherche);
		map.put("libelleShowTomorrow", "Réserver le " + DateUtil.dateToFullString(DateUtil.rechercherDateLejourSuivant(jourRecherche)));
		if (service.canBook(user, day)) {
			map.put("showTomorrow", day);
		}
		
		return new ModelAndView(map, "reservations.ftl");
	}
}
