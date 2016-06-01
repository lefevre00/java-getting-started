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

@Component
public class SearchRoute extends AuthenticatedRoute {

	@Autowired
	private DateService dateService;
	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request req, Response resp) {

		String paramDate = req.queryParams("day");
		String dateRecherchee = paramDate != null ? paramDate : dateToString(dateService.getNextWorkingDay());
		LocalDate dateRechercheeAsDate = DateUtil.stringToDate(dateRecherchee);

		Map<String, Object> map = Routes.getMap(req);
		map.put("dateBook", dateRecherchee);
		map.put("dateRecherche", DateUtil.dateToFullString(dateRechercheeAsDate));

		User user = getUser(req);
		Place bookedPlace = getBookedPlace(user, dateRechercheeAsDate);
		if (bookedPlace != null) {
			// L'utilisateur a déjà réservé une place ce jour là
			map.put("message", "Vous avez déjà réservé la place " + bookedPlace.getPlaceNumber());
		} else {
			List<Place> places = getPlaces(dateRechercheeAsDate);
			if (!places.isEmpty()) {
				map.put("place", places.get(0));
			}
		}

		return new ModelAndView(map, Templates.SEARCH);
	}

	private List<Place> getPlaces(LocalDate dateRecherche) {
		return placeService.getAvailablesAtDate(dateRecherche);
	}

	private Place getBookedPlace(User user, LocalDate dateRecherche) {
		return placeService.getBookedPlaceByUserAtDate(user, dateRecherche);
	}
}
