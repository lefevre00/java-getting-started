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

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
	private static List<Place> placeCache = new ArrayList<Place>();
	
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

//	public List<Integer> findAllFree() {
//		List<Integer> listFree = new ArrayList<Integer>();
//		for (Iterator<Place> iterator = placeCache.iterator(); iterator.hasNext();) {
//			Place place = (Place) iterator.next();
//			if(place.isFree())	listFree.add(place.getPlaceNumber());
//		}
//		
//		
//		
//		return listFree;
//	}

	public List<Integer> findAllFreeByDate(LocalDate date) throws ParseException {
		List<Integer> listFree = new ArrayList<Integer>();
		for (Iterator<Place> iterator = placeCache.iterator(); iterator.hasNext();) {
			Place place = (Place) iterator.next();
			
			String strDateRecherche = date.format(formatter);
			if(place.isFree() && (strDateRecherche.equalsIgnoreCase(place.getOccupationDate()))) listFree.add(place.getPlaceNumber());
		}
		
		return listFree;
	}
}
