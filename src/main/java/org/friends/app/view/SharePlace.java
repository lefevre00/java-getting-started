package org.friends.app.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
				String strDateDebut = paramRequest.queryParams("dateDebut");
				String strDateFin = paramRequest.queryParams("dateFin");
					
					List<String> lesDates = new ArrayList<String>();
					if((strDateDebut != null) && (strDateFin != null)){
						if(strDateDebut.equalsIgnoreCase(strDateFin)){
							lesDates.add(strDateDebut);
						}else{
							lesDates = getDaysBetweenDates(strtoDate(strDateDebut), strtoDate(strDateFin));
						}
						for (Iterator<String> iterator = lesDates.iterator(); iterator.hasNext();) {
							String leJour = (String) iterator.next();
							placeService.releasePlace(user.getPlaceNumber().intValue(), leJour);
						}
						
				        paramResponse.redirect("/protected/search");

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
	
	private Date strtoDate(String strdate) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(strdate);
	}
	
	
	public static List<String> getDaysBetweenDates(Date startdate, Date enddate)
	{
	    List<String> dates = new ArrayList<String>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(startdate);

	    while (calendar.getTime().before(enddate))
	    {
	        Date result = calendar.getTime();
	        dates.add(new SimpleDateFormat("dd/MM/yyyy").format(result));
	        calendar.add(Calendar.DATE, 1);
	    }
	    return dates;
	}
}

