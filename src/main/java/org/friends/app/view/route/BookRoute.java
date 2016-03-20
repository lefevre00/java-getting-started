package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.service.impl.BookingException;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

public class BookRoute extends AuthenticatedRoute {

	public final static String PARAM_PLACE = ":place_id";
	public final static String PARAM_DATE = ":date";
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);

	PlaceServiceBean service = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
		Map<String, String> params = request.params();
		Map<String, Object> map = new HashMap<>();

		String place = params.get(PARAM_PLACE);
		String date = params.get(PARAM_DATE);
		
		if (StringUtils.isEmpty(date)) {
		    date = LocalDate.now().format(formatter);
		}
		
		if (StringUtils.isEmpty(place)) {
			map.put("dateRecherche", "N/C");
			map.put("message", "Merci de sélectionner une place et une date pour compléter la réservation.");
		} else {
			try {
				map.put("dateRecherche", date);
				Place booked = service.book(date, getUser(request), place);
				map.put("message", "Place n°" + booked.getPlaceNumber() + " reservée.");
			} catch (BookingException e) {
				map.put("message", "Vous avez déjà réservé une place pour le jour demandée.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new ModelAndView(map , "book.ftl");
	}
}
