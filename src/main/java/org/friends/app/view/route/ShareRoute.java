package org.friends.app.view.route;

import java.security.AccessControlException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class ShareRoute implements TemplateViewRoute {
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private PlaceServiceBean placeService = new PlaceServiceBean();

	@Override
	public ModelAndView handle(Request paramRequest, Response paramResponse)
			throws Exception {

		ModelAndView model = null;
		Map<String, Object> map = new HashMap<>();
		User user = (User) paramRequest.session().attribute("user");
		if (user == null)
			throw new AccessControlException("An unauthenticated user cannont share a place");

		if ("POST".equalsIgnoreCase(paramRequest.requestMethod())) {
			LocalDate dateDebut = paramRequest.queryParams("dateDebut") != null ? LocalDate.parse(paramRequest.queryParams("dateDebut"), formatterDatePicker) : null;
			LocalDate dateFin = paramRequest.queryParams("dateFin") != null ? LocalDate.parse(paramRequest.queryParams("dateFin"), formatterDatePicker) : null;
				
			if((dateDebut != null) && (dateFin != null)){
				List<String> lesDates = getDaysBetweenDates(dateDebut, dateFin);
				for (Iterator<String> iterator = lesDates.iterator(); iterator.hasNext();) {
					String leJour = (String) iterator.next();
					placeService.releasePlace(user.getPlaceNumber().intValue(), leJour);
				}
				
		        paramResponse.redirect("/protected/search");
		        map.put("dateRecherche", "");
		        map.put("nextDay", "?nextDay="+ LocalDate.now().format(formatter));
		        map.put("yesteday", "?previousDay="+null);
		        map.put("places", new ArrayList<>());
		        model = new ModelAndView(map, "search.ftl");
			}
		} else {
			if (user.getPlaceNumber() == null)
				throw new RuntimeException("A user without place cannot share a place");

			Place place = new Place(user.getPlaceNumber(), user.getEmailAMDM(), LocalDate.now().format(DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN)));
			model = new ModelAndView(place, "sharePlace.ftl");
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
	        	dates.add(dateToAdd.format(formatter));

	        }
	    	dateToAdd= dateToAdd.plusDays(1);
	    }
	    return dates;
	}
}

