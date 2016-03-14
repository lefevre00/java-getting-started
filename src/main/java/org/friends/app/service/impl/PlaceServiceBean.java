package org.friends.app.service.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;

public class PlaceServiceBean {
	
	PlaceDao placedao = new PlaceDao();
	
	public List<Place> getAvailableByDate(LocalDate date) throws ParseException {
		return placedao.findAllFreeByDate(date);
	}
	
	public void releasePlace(Integer numberPlace, String dateReservation) {
		Place releasePlace = new Place(numberPlace.intValue(), null, dateReservation);
		placedao.persist(releasePlace);
	}
}
