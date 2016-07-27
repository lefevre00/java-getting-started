package org.friends.app.view.route;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Component
public class AdminRoute extends AdminAuthRoute {

	@Autowired
	private PlaceService placeService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		Map<String, Object> map = Routes.getMap(request);

		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			String paramDebut = request.queryParams("dateDebut");
			LocalDate dateDebut = paramDebut != null ? DateUtil.stringToDate(paramDebut, Locale.FRANCE) : null;
			String paramFin = request.queryParams("dateFin");
			LocalDate dateFin = paramFin != null ? DateUtil.stringToDate(paramFin, Locale.FRANCE) : null;

			List<Place> datesPartages = placeService.getAllPlaceBetweenTwoDates(DateUtil.dateToString(dateDebut),
					DateUtil.dateToString(dateFin));
			if (!datesPartages.isEmpty()) {
				map.put("datesPartages", datesPartages);
			}
		}

		return new ModelAndView(map, Templates.ADMIN_PAGE);

	}
}
