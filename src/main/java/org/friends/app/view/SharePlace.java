package org.friends.app.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class SharePlace implements TemplateViewRoute {
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private PlaceServiceBean placeService = new PlaceServiceBean();

	@Override
	public ModelAndView handle(Request paramRequest, Response paramResponse)
			throws Exception {

		ModelAndView model = null;
		Map<String, Object> map = new HashMap<>();
		User user = (User) paramRequest.session().attribute("user");
		if(user==null){
			paramResponse.redirect("/");
			
			model = new ModelAndView(map, "login.ftl");
		}else{
			if ("POST".equalsIgnoreCase(paramRequest.requestMethod())) {
				LocalDate dateDebut = paramRequest.queryParams("dateDebut") != null ? LocalDate.parse(paramRequest.queryParams("dateDebut"), formatterDatePicker) : null;
				LocalDate dateFin = paramRequest.queryParams("dateFin") != null ? LocalDate.parse(paramRequest.queryParams("dateFin"), formatterDatePicker) : null;
					
					List<String> lesDates = new ArrayList<String>();
					if((dateDebut != null) && (dateFin != null)){
						
						lesDates = getDaysBetweenDates(dateDebut, dateFin);
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
			}else{
				Place place = new Place(user.getPlaceNumber() != null ? user.getPlaceNumber().intValue() : null, true);
				model = new ModelAndView(place, "sharePlace.ftl");
			}
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

