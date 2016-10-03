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
		boolean canShare = user.getPlaceNumber() != null;
		String release = request.queryParams("release");
		if (StringUtils.isNotEmpty(release)) {
			placeService.release(user, release);
		}

		// on récupère les réservations pour des dates égales/postérieures à la
		// date du jour
		List<Place> reservations = placeService.getReservations(user);

		Map<String, Object> map = Routes.getMap(request);
		
		map.put("messageInfo", "");
		boolean reservationTodayPossible = false;
		boolean reservationTomorrowPossible = false;
		if (!reservations.isEmpty()) {
			map.put("places", reservations);
		}

		map.put("dateDuJour", DateUtil.dateToFullString(DateUtil.now()));

		LocalDate jour1 = dateService.getWorkingDay();
		String day = dateToString(jour1);
		map.put("libelleShowToday", DateUtil.dateToMediumString(jour1));
		// On vérifie qu'à cette date, le user n'a pas réservé de place et que des places sont disponibles 
		boolean hasReservedToday = placeService.canBook(user, day);
		boolean plusPlaceDispoToday = placeService.getAvailablesAtDate(jour1).size() > 0;
		if (hasReservedToday && plusPlaceDispoToday) {
			map.put("showToday", day);
			reservationTodayPossible = true;
		}

		
		LocalDate jour2 = dateService.getNextWorkingDay(jour1);
		day = dateToString(jour2);
		map.put("libelleShowTomorrow", DateUtil.dateToMediumString(jour2));
		// On vérifie qu'à cette date, le user n'a pas réservé de place et que des places sont disponibles
		boolean hasReservedTomorrow = placeService.canBook(user, day);
		boolean plusPlaceDispoTomorrow = placeService.getAvailablesAtDate(jour2).size() > 0;
		if (hasReservedTomorrow && plusPlaceDispoTomorrow ) {
			map.put("showTomorrow", day);
			reservationTomorrowPossible = true;
		}
		
		
		if(!reservationTomorrowPossible && !reservationTodayPossible){
			String messageInfo = "Vous ne pouvez pas effectuer de réservation ";
			messageInfo += canShare ? "car votre place est disponible ou inoccupée." : "car il n'y a pas de places disponibles";
			map.put("info", messageInfo);
		}else if(!reservationTomorrowPossible || !reservationTodayPossible){
			String messageInfo = "Vous ne pouvez pas effectuer de réservation le ";
			if(!reservationTodayPossible) {
				messageInfo += DateUtil.dateToMediumString(jour1);
				if(canShare) {
					messageInfo += " car votre place est disponible ou inoccupée.";
				} else {
					if(!hasReservedToday) {
						messageInfo += " car vous avez déjà réservé une place.";
					} else if(!plusPlaceDispoToday){
						messageInfo += " car il n'y a pas de places disponibles.";
					}
				}
			}
			if(!reservationTomorrowPossible) {
				messageInfo += DateUtil.dateToMediumString(jour2);
				if(canShare) {
					messageInfo += " car votre place est disponible ou inoccupée.";
				} else {
					if(!hasReservedTomorrow) {
						messageInfo += " car vous avez déjà réservé une place.";
					} else if(!plusPlaceDispoTomorrow){
						messageInfo += " car il n'y a pas de places disponibles.";
					}
				}
			}
			map.put("info", messageInfo);
		}
		

		return new ModelAndView(map, Templates.RESERVATIONS);
	}
}
