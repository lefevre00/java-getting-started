package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.BookingException;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

public class BookRoute extends AuthenticatedRoute {

	public final static String PARAM_PLACE = ":place_id";
	public final static String PARAM_DATE = ":date";

	PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		User user = getUser(request);
		Map<String, String> params = request.params();
		Map<String, Object> map = getMap();
		
		String place = params.get(PARAM_PLACE);
		String date = params.get(PARAM_DATE);
		map.put("shared", user.getPlaceNumber()==null ? null : true);
		if (StringUtils.isEmpty(date)) {
		    date = DateUtil.dateToString(LocalDate.now());
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

		return new ModelAndView(map , "book.ftl");
	}
}
