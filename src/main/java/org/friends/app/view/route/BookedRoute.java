package org.friends.app.view.route;

import static org.friends.app.util.DateUtil.dateToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.DateServiceBean;
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

	private DateServiceBean dateService = new DateServiceBean();
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
		
		map.put("dateDuJour", DateUtil.dateToFullString(DateUtil.now()));

		LocalDate jour1 = dateService.getWorkingDay();
		String day = dateToString(jour1);
		map.put("libelleShowToday", "Réserver " + DateUtil.dateToMediumString(jour1));
		if (service.canBook(user, day)) {
			map.put("showToday", day);
		}
		
		LocalDate jour2 = dateService.getNextWorkingDay(jour1); 
		day =  dateToString(jour2);
		map.put("libelleShowTomorrow", "Réserver " + DateUtil.dateToMediumString(jour2));
		if (service.canBook(user, day)) {
			map.put("showTomorrow", day);
		}
		
		return new ModelAndView(map, "reservations.ftl");
	}
}
