package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;
import org.friends.app.util.DateUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SearchRoute extends AuthenticatedRoute {
	
	@Deprecated // Move to DateUtil
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy");

	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView doHandle(Request req, Response resp) {
    	Map<String, Object> map = getMap();
    	User user = getUser(req);
    	
    	LocalDate now = LocalDate.now();
    	String paramDate = req.queryParams("day");
		String dateRecherchee = paramDate != null ? paramDate : rechercheLaProchaineDateUtilisable();
		LocalDate dateRechercheeAsDate = DateUtil.stringToDate(dateRecherchee);
		
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
    	Place placeReserveeParleUSer = placeReservedByUserAtTheDate(user, dateRechercheeAsDate);
    	List<Place> places = new ArrayList<Place>();
    	
    	
    	if(placeReserveeParleUSer != null){
    		// L'utilisateur a d�j� r�serv� une place ce jour l�
    		map.put("message", "Vous avez déjà réservé la place " + placeReserveeParleUSer.getPlaceNumber());
    	}else{
			try {
				places = getPlaces(dateRechercheeAsDate);
			} catch (SQLException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if (!places.isEmpty()){
	    		map.put("place", places.get(0));
	    	}
	    	
    	}
        return new ModelAndView(map, "search.ftl");
	}

	protected String rechercherLejourPrecedent(LocalDate dateRecherche) {
		if(DayOfWeek.MONDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.minusDays(3);
		}else{
			dateRecherche = dateRecherche.minusDays(1);
		}
		return dateRecherche.compareTo(LocalDate.now()) <0 ? null : DateUtil.dateAsString(dateRecherche);
	}

	/**
	 * Retourne la prochaine date de reservation
	 * @return
	 */
	protected String rechercheLaProchaineDateUtilisable(){
		LocalDate dateUtilisable = LocalDate.now();
		if(DayOfWeek.SUNDAY.equals(dateUtilisable.getDayOfWeek())){
			dateUtilisable = dateUtilisable.plusDays(3);
		}else if(DayOfWeek.SATURDAY.equals(dateUtilisable.getDayOfWeek())){
			dateUtilisable = dateUtilisable.plusDays(2);
		}
		return DateUtil.dateAsString(dateUtilisable);
	}

	private List<Place> getPlaces(LocalDate dateRecherche) throws SQLException, URISyntaxException {
		return placeService.getAvailableByDate(dateRecherche);
	}
	
	/**
	 * Retourne la place réservée par une utilisateur à une date donnée
	 * retourne null si il n'a pas réservé de place
	 * @param user
	 * @param dateRecherche
	 * @return
	 */
	private Place placeReservedByUserAtTheDate(User user, LocalDate dateRecherche){
		return placeService.getPlaceReservedByUserAtTheDate(user, dateRecherche);
	}
}
