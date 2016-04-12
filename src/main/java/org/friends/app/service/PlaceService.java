package org.friends.app.service;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.BookingException;

public interface PlaceService {

	String INVALID_NUMBER = null;

	public List<Place> getAvailableByDate(LocalDate date)  throws SQLException, URISyntaxException;
	
	public void releasePlace(Integer numberPlace, LocalDate dateReservation) throws SQLException, URISyntaxException, BookingException;
	
	public Place book(String date, User user, String placeNumber) throws SQLException, URISyntaxException, BookingException;
	
	public List<Place> getReservationsOrRelease(User user) throws SQLException, URISyntaxException;
	
	public List<String> getAllsharedDatesByPlaceNumber(Integer placeNumber);

}
