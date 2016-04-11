package org.friends.app.view.route;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.PlaceServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SearchRoute extends AuthenticatedRoute {
	
	static DateTimeFormatter formatterDatePicker = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy");

	private PlaceServiceBean placeService = new PlaceServiceBean();
	
	@Override
	public ModelAndView doHandle(Request req, Response resp) {
    	Map<String, Object> map = getMap();
    	User user = getUser(req);
    	
    	LocalDate now = LocalDate.now();
    	String paramDate = req.queryParams("day");
		String dateRecherchee = paramDate != null ? paramDate : rechercheLaProchaineDateUtilisable();
		LocalDate dateRechercheeAsDate = LocalDate.parse(dateRecherchee, PlaceDao.formatter);
		
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
    	map.put("shared", user.getPlaceNumber()==null ? null : true);
    	Place placeReserveeParleUSer = placeReservedByUserAtTheDate(user, dateRechercheeAsDate);
    	List<Place> places = new ArrayList<Place>();
    	
    	
    	if(placeReserveeParleUSer != null){
    		// L'utilisateur a d�j� r�serv� une place ce jour l�
    		map.put("message", "Vous avez d�j� r�serv� la place " + placeReserveeParleUSer.getPlaceNumber());
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
		return dateRecherche.compareTo(LocalDate.now()) <0 ? null : dateRecherche.format(PlaceDao.formatter);
	}

	/**
	 * Retourne la prochaine date de r�servation
	 * @return
	 */
	protected String rechercheLaProchaineDateUtilisable(){
		LocalDate dateUtilisable = LocalDate.now();
		if(DayOfWeek.SUNDAY.equals(dateUtilisable.getDayOfWeek())){
			dateUtilisable = dateUtilisable.plusDays(3);
		}else if(DayOfWeek.SATURDAY.equals(dateUtilisable.getDayOfWeek())){
			dateUtilisable = dateUtilisable.plusDays(2);
		}
		return dateUtilisable.format(PlaceDao.formatter);
	}

	private List<Place> getPlaces(LocalDate dateRecherche) throws SQLException, URISyntaxException {
		return placeService.getAvailableByDate(dateRecherche);
	}
	
	/**
	 * Retourne la place r�serv�e par une utilisateur � une date donn�e
	 * retourne null si il n'a pas r�serv� de place
	 * @param user
	 * @param dateRecherche
	 * @return
	 */
	private Place placeReservedByUserAtTheDate(User user, LocalDate dateRecherche){
		return placeService.getPlaceReservedByUserAtTheDate(user, dateRecherche);
	}
}
