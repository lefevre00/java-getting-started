package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.BookingException;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class BookRoute extends AuthenticatedRoute {

	public final static String PARAM_PLACE = ":place_id";
	public final static String PARAM_DATE = ":date";

	@Autowired
	PlaceService service;

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		User user = getUser(request);
		Map<String, String> params = request.params();
		Map<String, Object> map = Routes.getMap(request);

		String place = params.get(PARAM_PLACE);
		String date = params.get(PARAM_DATE);
		if (StringUtils.isEmpty(date)) {
			date = DateUtil.dateToString(DateUtil.now());
		}

		if (StringUtils.isEmpty(place)) {
			map.put("dateRecherche", "N/C");
			map.put("message", "Merci de sélectionner une place et une date pour compléter la réservation.");
			map.put("numeroPlace", "N/C");
		} else {
			try {
				map.put("dateRecherche", date);
				Place booked = service.book(date, user, place);
				map.put("message", "");
				map.put("numeroPlace", booked.getPlaceNumber().toString());
			} catch (BookingException e) {
				map.put("message", "Vous avez déjà réservé une place pour le jour demandée.");
				map.put("numeroPlace", "");
				map.put("dateRecherche", "");
			}
		}

		return new ModelAndView(map, Templates.BOOK);
	}
}
