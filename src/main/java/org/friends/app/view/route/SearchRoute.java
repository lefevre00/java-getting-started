package org.friends.app.view.route;

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

public class SearchRoute extends AuthenticatedRoute {
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy");

	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView doHandle(Request req, Response resp) {
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
    	List<Place> places = getPlaces(dateRechercheeAsDate);
    	if (!places.isEmpty())
    		map.put("places", places);
    	
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

	private List<Place> getPlaces(LocalDate dateRecherche) {
		return placeService.getAvailableByDate(dateRecherche);
	}
}
