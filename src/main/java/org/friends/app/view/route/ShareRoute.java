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
import org.friends.app.service.impl.BookingException;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ShareRoute extends AuthenticatedRoute {
	
	private PlaceServiceBean placeService = new PlaceServiceBean();
	private static List<String> datesInseres = new ArrayList<>();
	
	@Override
	public ModelAndView doHandle(Request request, Response response) {
		
    	Map<String, Object> map = getMap();
    	User user = getUser(request);		

		ModelAndView model = null;
		
		List<Place> partages = placeService.getReservationsOrRelease(user);
		
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			
			LocalDate dateDebut = request.queryParams("dateDebut") != null ? DateUtil.stringToDate(request.queryParams("dateDebut"), Locale.FRANCE) : null;
			LocalDate dateFin = request.queryParams("dateFin") != null ? DateUtil.stringToDate(request.queryParams("dateFin"), Locale.FRANCE) : null;

			if((dateDebut != null) && (dateFin != null)){
				List<String> lesDates = getDaysBetweenDates(dateDebut, dateFin);
				int i=0;
				for (Iterator<String> iterator = lesDates.iterator(); iterator.hasNext();) {
					String leJour = (String) iterator.next();
					try {
						placeService.releasePlace(user.getPlaceNumber().intValue(), DateUtil.stringToDate(leJour));
					} catch (BookingException e) {
						map.put("placeNumber", user.getPlaceNumber());
						map.put("message", "Vous avez déjà partagé votre place pour le " + datesInseres.get(i));
				        return new ModelAndView(map, "error.ftl");	
					}
					i++;
				}
				

				map.put("datesPartage", partages);
				map.put("placeNumber", user.getPlaceNumber());
		        model = new ModelAndView(map, "sharePlace.ftl");

			}
		} else {
			if (user.getPlaceNumber() == null){
				throw new RuntimeException("A user without place cannot share a place");
			}
			map.put("datesPartage", partages);
	        map.put("placeNumber", user.getPlaceNumber());
			model = new ModelAndView(map, "sharePlace.ftl");
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
	        	datesInseres.add(DateUtil.dateToString(dateToAdd, Locale.FRANCE));

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

