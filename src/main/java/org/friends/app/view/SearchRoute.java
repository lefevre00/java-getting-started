package org.friends.app.view;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView handle(Request req, Response resp) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	
    	String dateRecherchee = req.queryParams("nextDay")!= null ? req.queryParams("nextDay") : LocalDate.now().format(formatter);
    	LocalDate timePoint = LocalDate.now();
    	LocalDate jourSuivant  = dateRecherchee != null ? LocalDate.parse(dateRecherchee, formatter) : timePoint;
    	String strYesteday = null;
    	if(req.queryParams("nextDay")!= null){
    		strYesteday = rechercherLejourPrecedent(LocalDate.parse(dateRecherchee, formatter));
    	}
    	//String strdateRecherche = dateParametre != null ? LocalDate.now().format(formatter) :  timePoint.format(formatter);
    	String strTomorrow = rechercherLejourSuivant(jourSuivant);
    	map.put("nextDay", "?nextDay="+strTomorrow);
    	
    	if (strYesteday != null)
    		map.put("yesteday", "?previousDay="+strYesteday);
    	
    	map.put("dateRecherche", LocalDate.parse(dateRecherchee, formatter).format(formatterDatePicker));
    	map.put("places", getPlaces(LocalDate.parse(dateRecherchee, formatter)));
        return new ModelAndView(map, "search.ftl");
	}

	private String rechercherLejourSuivant(LocalDate dateRecherche) {
		if(DayOfWeek.FRIDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.plusDays(3);
		}else{
			dateRecherche = dateRecherche.plusDays(1);
		}
		return dateRecherche.format(formatter);//new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateRecherche.getTime() + (1000 * 60 * 60 * 24 * nextDay)));
	}
	
	private String rechercherLejourPrecedent(LocalDate dateRecherche) {
		if(DayOfWeek.MONDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.minusDays(3);
		}else{
			dateRecherche = dateRecherche.minusDays(1);
		}
		return dateRecherche.format(formatter);//new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateRecherche.getTime() + (1000 * 60 * 60 * 24 * nextDay)));
	}

	private List<Place> getPlaces(LocalDate dateRecherche) throws ParseException {
		List<Place> places = new ArrayList<>();
		List<Integer> freePlaces = placeService.getAvailableByDate(dateRecherche);
		for (Iterator<Integer> iterator = freePlaces.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			places.add(new Place(integer.intValue(), freePlaces.contains(integer.intValue())));
		}
		
		
//		for (int i = 1; i<150; i++) {
//			places.add(new Place(i, freePlaces.contains(i)));
//		}
		return places;
	}
	

}
