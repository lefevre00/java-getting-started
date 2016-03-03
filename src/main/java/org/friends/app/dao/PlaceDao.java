package org.friends.app.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.friends.app.model.Place;

import spark.utils.Assert;

public class PlaceDao {

	private static List<Place> placeCache = new ArrayList<Place>();
    
    static{
    	placeCache.add(new Place(1, true));
    	placeCache.add(new Place(2, false));
    	placeCache.add(new Place(3, false));
    	placeCache.add(new Place(33, true));
    }		
	
	public Place persist(Place place) {
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
}
