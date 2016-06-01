package org.friends.app.view.route;

import static org.friends.app.util.DateUtil.dateToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.DateService;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

/**
 * Page listing the booked places for days starting today. Give access to
 * booking page.
 * 
 * @author michael
 *
 */
@Component
public class BookedRoute extends AuthenticatedRoute {

	@Autowired
	private DateService dateService;
	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		User user = getUser(request);

		String release = request.queryParams("release");
		if (StringUtils.isNotEmpty(release)) {
			placeService.release(user, release);
		}

		// on récupère les réservations pour des dates égales/postérieures à la
		// date du jour
		List<Place> reservations = placeService.getReservations(user);

		Map<String, Object> map = Routes.getMap(request);
		if (!reservations.isEmpty()) {
			map.put("places", reservations);
		}

		map.put("dateDuJour", DateUtil.dateToFullString(DateUtil.now()));

		LocalDate jour1 = dateService.getWorkingDay();
		String day = dateToString(jour1);
		map.put("libelleShowToday", DateUtil.dateToMediumString(jour1));
		if (placeService.canBook(user, day)) {
			map.put("showToday", day);
		}

		LocalDate jour2 = dateService.getNextWorkingDay(jour1);
		day = dateToString(jour2);
		map.put("libelleShowTomorrow", DateUtil.dateToMediumString(jour2));
		if (placeService.canBook(user, day)) {
			map.put("showTomorrow", day);
		}

		return new ModelAndView(map, Templates.RESERVATIONS);
	}
}
