package org.friends.app.dao;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.friends.app.model.Place;

import spark.utils.Assert;

public class PlaceDao {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static List<Place> placeCache = new ArrayList<Place>();
	static LocalDate timePoint = LocalDate.now();
	static String strDateToday  = timePoint.format(formatter);
	static String strTomorrow = timePoint.plusYears(1).format(formatter);
	static String strYearsteday = timePoint.minusDays(1).format(formatter);
	
    static{
    	placeCache.add(new Place(1, true, strDateToday));//Place libre aujourd'hui free = true
    	placeCache.add(new Place(2, false, strDateToday));//Place occupée aujourd'hui
    	placeCache.add(new Place(3, "damien.urvoix@amdm.fr", strDateToday));//place occupée aujourd'hui
    	placeCache.add(new Place(4, null, strDateToday)); // place libre  aujourd'hui
    	placeCache.add(new Place(33, true, strDateToday)); //Place libre aujourd'hui free = true
    	placeCache.add(new Place(34, true, strYearsteday)); //Place libre hier free = true
    	placeCache.add(new Place(35, true, strTomorrow)); //Place libre dans le futur free = true
    	placeCache.add(new Place(36, null, strYearsteday)); //Place libre hier occupee == null
    	placeCache.add(new Place(37, null, strTomorrow)); //Place libre dans le futur occupee == null
    }		
	
	public Place persist(Place place) {
		/*En sql faire : 
		 * INSERT INTO reservations (placeNumber, occupiedBy, occupationDate)
SELECT 23, null, DATE('31/03/2016')
  FROM reservations
 WHERE NOT EXISTS (SELECT 1 
                     FROM reservations 
                    WHERE placeNumber = 23
                      AND occupationDate = DATE('2013-02-12'));
		 * 
		 * 
		 * */
		Assert.notNull(place);
		placeCache.add(place);
		return place;
	}

	public List<Integer> findAllFree() {
		List<Integer> listFree = new ArrayList<Integer>();
		for (Iterator<Place> iterator = placeCache.iterator(); iterator.hasNext();) {
			Place place = (Place) iterator.next();
			if(place.isFree())	listFree.add(place.getPlaceNumber());
		}
		
		return listFree;
	}

	public List<Integer> findAllFree(LocalDate date) throws ParseException {
		List<Integer> listFree = new ArrayList<Integer>();
		for (Iterator<Place> iterator = placeCache.iterator(); iterator.hasNext();) {
			Place place = (Place) iterator.next();
			
			String strDateRecherche = date.format(formatter);
			if(place.isFree() && (strDateRecherche.equalsIgnoreCase(place.getOccupationDate()))) listFree.add(place.getPlaceNumber());
		}
		
		return listFree;
	}
}
