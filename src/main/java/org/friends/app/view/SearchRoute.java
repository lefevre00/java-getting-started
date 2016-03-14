package org.friends.app.view;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class SearchRoute implements TemplateViewRoute {
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy");

	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView handle(Request req, Response resp) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	
    	LocalDate now = LocalDate.now();
    	String paramDate = req.queryParams("day");
		String dateRecherchee = paramDate != null ? paramDate : now.format(formatter);
		LocalDate dateRechercheeAsDate = LocalDate.parse(dateRecherchee, formatter);

    	// Previous date
    	String previous = null;
		if (paramDate != null) {
    		previous = rechercherLejourPrecedent(dateRechercheeAsDate);
    		map.put("previous", previous);
    	}
    	
    	// Next date
    	LocalDate nextDate  = dateRecherchee != null ? dateRechercheeAsDate : now;
    	String next = rechercherLejourSuivant(nextDate);
    	map.put("next", next);
    	
    	map.put("dateRecherche", dateRechercheeAsDate.format(formatterDatePicker));
    	map.put("dateBook", dateRecherchee);
    	map.put("places", getPlaces(dateRechercheeAsDate));
    	
        return new ModelAndView(map, "search.ftl");
	}

	private String rechercherLejourSuivant(LocalDate dateRecherche) {
		if(DayOfWeek.FRIDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.plusDays(3);
		}else{
			dateRecherche = dateRecherche.plusDays(1);
		}
		return dateRecherche.format(formatter);
	}
	
	private String rechercherLejourPrecedent(LocalDate dateRecherche) {
		if(DayOfWeek.MONDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.minusDays(3);
		}else{
			dateRecherche = dateRecherche.minusDays(1);
		}
		return dateRecherche.format(formatter);
	}

	private List<Place> getPlaces(LocalDate dateRecherche) throws ParseException {
		return placeService.getAvailableByDate(dateRecherche);
	}
}
