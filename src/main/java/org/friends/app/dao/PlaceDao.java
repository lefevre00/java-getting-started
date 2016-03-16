package org.friends.app.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

	public List<Place> findAllFreeByDate(LocalDate date) {
		List<Place> listFree = new ArrayList<Place>();
		String strDateRecherche = date.format(formatter);

		for (Iterator<Place> iterator = placeCache.iterator(); iterator.hasNext();) {
			Place place = (Place) iterator.next();
			
			if (place.getOccupiedBy() == null && strDateRecherche.equalsIgnoreCase(place.getOccupationDate()))
				listFree.add(place);
		}
		
		return listFree;
	}

	public Place findFirst(Predicate<Place> predicate) {
		return placeCache.stream().filter(predicate).findFirst().orElse(null);
	}

	public List<Place> findAll(Predicate<Place> predicate) {
		return placeCache.stream().filter(predicate).collect(Collectors.toList());
	}
}
