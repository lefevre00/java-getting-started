package org.friends.app.service;

import java.time.LocalDate;
import java.util.List;

import org.friends.app.model.Place;
import org.friends.app.model.User;

public interface PlaceService {

	public List<Place> getAvailablesAtDate(LocalDate date);

	public Place book(String date, User user, String placeNumber) throws BookingException;

	public List<Place> getShared(User user);

	/**
	 * Cancel a shared place by the owner of the place
	 * 
	 * @param user
	 * @param date
	 * @throws UnshareException
	 */
	void unshare(User user, String date) throws UnshareException;

	/**
	 * The owner share his place between to dates.
	 * 
	 * @param user
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 * @throws Exception
	 */
	public boolean sharePlaces(User user, LocalDate dateDebut, LocalDate dateFin, String emailOccupant) throws Exception;

	/**
	 * A user which booked a place, inform that he don't need the place anymore.
	 * 
	 * @param user
	 * @param release
	 */
	public void release(User user, String release);

	/**
	 * Get booked places, starting now.
	 * 
	 * @param user
	 * @return The list of places booked, or empty list if none.
	 */
	public List<Place> getReservations(User user);

	public boolean canBook(User user, String day);

	public Place isPlaceShared(Integer placeNumber, String dateToString);

	public Place getBookedPlaceByUserAtDate(User user, LocalDate dateRecherche);

	/**
	 * Get all places, between two dates
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Place> getAllPlaceBetweenTwoDates(String beginDate, String endDate);
	
	/**
	 * Retourne toutes les dates de partages d'une place
	 * 
	 * @param placeNumber
	 * @return
	 */
	public List<Place> getAllSharedDatesByUser(Integer placeNumber);
	
	/**
	 * Retourne toutes les places réservées par un utilisateurs
	 * 
	 * @param email
	 * @return
	 */
	public List<Place> getAllPlacesBookedByUser(String email);
	
}
