package org.friends.app.view.route;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

public class ShareRoute extends AuthenticatedRoute {
	
	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
    	Map<String, Object> map = getMap();
    	User user = getUser(request);		

		ModelAndView model = null;
		map.put("shared", user.getPlaceNumber()==null ? null : true);
		
		// Annulation d'un partage
		String unshareDate = request.queryParams("unshareDate");
		String placeNumber = request.queryParams("placeNumber");
		if (!StringUtils.isEmpty(unshareDate)){
			Place placeReservee= null;
			if(user.getPlaceNumber() != null){
					placeReservee = new Place(user.getPlaceNumber(), unshareDate);
			}else{
				if(StringUtils.isEmpty(placeNumber)){
					map.put("placeNumber", placeNumber);
					map.put("message", "Une erreur est survenue lors de l'annulation !"); 
			        return new ModelAndView(map, "error.ftl");
				}
				placeReservee = placeService.getPlaceReservedByUserAtTheDate(user, DateUtil.stringToDate(unshareDate));
			}
			
			try {
				placeService.unsharePlaceByDate(placeReservee, user);
			} catch (Exception e) {
				map.put("placeNumber", user.getPlaceNumber());
				map.put("message", "Une erreur est survenue lors de l'annulation !"); 
		        return new ModelAndView(map, "error.ftl");	
			}
			
		}
		
		// Liste des dates partagées
		List<Place> datesPartages = placeService.getReservationsOrRelease(user);
		if (!datesPartages.isEmpty()){
			map.put("datesPartages", datesPartages);
		}
		
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			
			LocalDate dateDebut = request.queryParams("dateDebut") != null ? DateUtil.stringToDate(request.queryParams("dateDebut"), Locale.FRANCE) : null;
			LocalDate dateFin = request.queryParams("dateFin") != null ? DateUtil.stringToDate(request.queryParams("dateFin"), Locale.FRANCE) : null;

			if((dateDebut != null) && (dateFin != null)){
				List<String> lesDates = getDaysBetweenDates(dateDebut, dateFin);
				for (Iterator<String> iterator = lesDates.iterator(); iterator.hasNext();) {
					String leJour = (String) iterator.next();
					try {
						placeService.releasePlace(user.getPlaceNumber().intValue(), DateUtil.stringToDate(leJour));
					} catch (Exception e) {
						map.put("placeNumber", user.getPlaceNumber());
						map.put("message", "Vous avez déjà partagé votre place pour le " + leJour);
				        return new ModelAndView(map, "error.ftl");	
					}
				}
				
				map.put("placeNumber", user.getPlaceNumber());
		        model = new ModelAndView(map, "sharePlace.ftl");
		        response.redirect(Routes.PLACE_SHARE);

			}
		} else {
			if (StringUtils.isEmpty(unshareDate) && (user.getPlaceNumber() == null)){
					throw new RuntimeException("A user without place cannot share a place");
			}
			map.put("placeNumber", user.getPlaceNumber());
			String viewName = "sharePlace.ftl";
			if (!StringUtils.isEmpty(unshareDate) && !StringUtils.isEmpty(placeNumber)) {
				map.put("presentation", user.getPlaceNumber() == null ? "Voici les places que vous avez réservées :" : "Voici les dates de libération de la place " + user.getPlaceNumber().toString() + " :");
				List<Place> reservations = placeService.getReservationsOrRelease(user);
				map.put("places", reservations);
				map.put("placenumber", user.getPlaceNumber() == null ? "" : user.getPlaceNumber());
				map.put("dateReservation",  DateUtil.stringToDate(getDateReservation(reservations)));
				viewName = "reservations.ftl";
			}
	        
			model = new ModelAndView(map, viewName);
		}

		return model;
	}
	
	public static List<String> getDaysBetweenDates(LocalDate startdate, LocalDate enddate)
	{
	    List<String> dates = new ArrayList<String>();
	    LocalDate dateToAdd = startdate;
	    
	    while (dateToAdd.isBefore(enddate.plusDays(1)))
	    {
	    	if((DayOfWeek.SATURDAY.equals(dateToAdd.getDayOfWeek())) || (DayOfWeek.SUNDAY.equals(dateToAdd.getDayOfWeek()))){
	        	
	        }else{
	        	dates.add(DateUtil.dateToString(dateToAdd));
	        }
	    	dateToAdd= dateToAdd.plusDays(1);
	    }
	    return dates;
	}
	
	public String nextDayWithOutWeekEnd(){
		String retour = DateUtil.dateToString(LocalDate.now());
		return retour;
	}
}

