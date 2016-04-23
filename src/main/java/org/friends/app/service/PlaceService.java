package org.friends.app.service;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.impl.BookingException;
import org.friends.app.service.impl.UnshareException;

public interface PlaceService {

	String INVALID_NUMBER = null;

	public List<Place> getAvailableByDate(LocalDate date);
	
	public Place book(String date, User user, String placeNumber) throws BookingException;
	
	public List<Place> getReservationsOrRelease(User user);

	void unsharePlaceByDate(User user, String date) throws UnshareException;

	public boolean sharePlaces(User user, LocalDate dateDebut, LocalDate dateFin) throws Exception;
}
