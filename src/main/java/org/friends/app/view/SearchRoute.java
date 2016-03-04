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
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class SearchRoute implements TemplateViewRoute {

	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView handle(Request req, Response resp) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	Date dateRecherche = new Date();
    	
    	String strdateRecherche = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateRecherche.getTime()));
    	String strTomorrow = rechercherLejourSuivant(dateRecherche);
//    	//if ("POST".equalsIgnoreCase(req.requestMethod())) {
    		if(req.queryParams("nextDay") != null){
    			dateRecherche = strtoDate(req.queryParams("nextDay"));
    			strdateRecherche = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateRecherche.getTime()));
    			strTomorrow = rechercherLejourSuivant(dateRecherche);
	    	}
    		map.put("nextDay", "?nextDay="+strTomorrow);
    		map.put("dateRecherche", strdateRecherche);
    	map.put("places", getPlaces(dateRecherche));
        return new ModelAndView(map, "search.ftl");
	}

	private String rechercherLejourSuivant(Date dateRecherche) {
		int nextDay = 1;
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(dateRecherche);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.FRIDAY){
			nextDay = 3;
		}
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateRecherche.getTime() + (1000 * 60 * 60 * 24 * nextDay)));
	}

	private List<Place> getPlaces(Date dateRecherche) throws ParseException {
		List<Place> places = new ArrayList<>();
		List<Integer> freePlaces = placeService.getAvailableByDate(dateRecherche);
		for (Iterator iterator = freePlaces.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			places.add(new Place(integer.intValue(), freePlaces.contains(integer.intValue())));
		}
		
		
//		for (int i = 1; i<150; i++) {
//			places.add(new Place(i, freePlaces.contains(i)));
//		}
		return places;
	}
	
	private Date strtoDate(String strdate) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(strdate);
	}
}
