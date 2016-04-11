package org.friends.app.view.route;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.friends.app.model.User;
import org.friends.app.service.impl.BookingException;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ShareRoute extends AuthenticatedRoute {
	@Deprecated // Move to DateUtil
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private PlaceServiceBean placeService = new PlaceServiceBean();

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		ModelAndView model = null;
		Map<String, Object> map = getMap();
		User user = getUser(request);
        map.put("dateRecherche", LocalDate.now().format(formatterDatePicker));
        String next = rechercherLejourSuivant(LocalDate.now());
    	map.put("next", next);
    	map.put("previous", null);
    	map.put("dateBook", DateUtil.dateAsString(LocalDate.now()));
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			LocalDate dateDebut = request.queryParams("dateDebut") != null ? LocalDate.parse(request.queryParams("dateDebut"), formatterDatePicker) : null;
			LocalDate dateFin = request.queryParams("dateFin") != null ? LocalDate.parse(request.queryParams("dateFin"), formatterDatePicker) : null;
				
			if((dateDebut != null) && (dateFin != null)){
				List<String> lesDates = getDaysBetweenDates(dateDebut, dateFin);
				for (Iterator<String> iterator = lesDates.iterator(); iterator.hasNext();) {
					String leJour = (String) iterator.next();
					try {
						placeService.releasePlace(user.getPlaceNumber().intValue(), DateUtil.stringToDate(leJour));
					} catch (BookingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		        model = new ModelAndView(map, "search.ftl");
		        response.redirect(Routes.PLACE_SEARCH);
			}
		} else {
			if (user.getPlaceNumber() == null)
				throw new RuntimeException("A user without place cannot share a place");
			

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
	    	System.out.println(dateToAdd.toString() + " / " + startdate.toString() + " - " + enddate.toString());
	    	if((DayOfWeek.SATURDAY.equals(dateToAdd.getDayOfWeek())) || (DayOfWeek.SUNDAY.equals(dateToAdd.getDayOfWeek()))){
	        	
	        }else{
	        	dates.add(DateUtil.dateAsString(dateToAdd));

	        }
	    	dateToAdd= dateToAdd.plusDays(1);
	    }
	    return dates;
	}
	
	public String nextDayWithOutWeekEnd(){
		String retour = DateUtil.dateAsString(LocalDate.now());
		return retour;
	}
}

