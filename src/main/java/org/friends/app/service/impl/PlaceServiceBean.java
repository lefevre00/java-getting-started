package org.friends.app.service.impl;

import static java.lang.String.format;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.PlaceService;
import org.hibernate.criterion.Restrictions;

import spark.utils.Assert;

public class PlaceServiceBean implements PlaceService{
	
	PlaceDao placedao = new PlaceDao();
	
	public List<Place> getAvailableByDate(LocalDate date) throws SQLException, URISyntaxException {
		return placedao.findPlacesByCriterions(Restrictions.eq("occupationDate", date.format(DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN))), Restrictions.isNull("mailOccupant"));
	}
	
	/**
	 * Release a place at date
	 * @param numberPlace
	 * @param dateReservation
	 * @throws SQLException
	 * @throws URISyntaxException
	 * @throws BookingException
	 */
	public void releasePlace(Integer numberPlace, LocalDate dateReservation) throws SQLException, URISyntaxException, BookingException {
		Assert.notNull(numberPlace);
		Assert.notNull(dateReservation);
		List<Place> listPlaceReserve = placedao.findPlacesByCriterions(Restrictions.eq("occupationDate", dateReservation.format(DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN))), Restrictions.eq("placeNumber", numberPlace));
		if(listPlaceReserve!= null && listPlaceReserve.size()>0){
				throw new BookingException(format("The place %s can't be book on %s", numberPlace.intValue(), dateReservation));
		}else  {
				placedao.persist(new Place(numberPlace.intValue(), dateReservation.format(PlaceDao.formatter)));
			}
	}

	/**
	 * Book a place for a user, on the given date.
	 * @param date, when the user need the place
	 * @param user, indicate who need the place
	 * @param placeNumber, place asked, could be null. If so, we provide the first available place.
	 * @return A place occupied by the user, if one is available.
	 * @throws BookingException When a user try to book while he already booked a place.
	 * @throws URISyntaxException 
	 * @throws SQLException 
	 */
	public Place book(String date, User user, String placeNumber) throws SQLException, URISyntaxException, BookingException {
		
		Assert.notNull(date);
		Assert.notNull(placeNumber);
		Assert.notNull(user);
		
		List<Place> listPlaceReserve = placedao.findPlacesByCriterions(Restrictions.eq("occupationDate", date), Restrictions.eq("placeNumber", placeNumber));

		Place booked = (listPlaceReserve!= null && listPlaceReserve.size()>0) ? listPlaceReserve.get(0) : null;//!placedao.findPlaceisFreeAtTheDate(Integer.valueOf(placeNumber),dateAsDate);
		if (booked==null)
			throw new BookingException(format("The place %s can't be booked  by the user %s on %d", placeNumber, user.getEmailAMDM(), date));
		
		if (booked.getOccupiedBy() != null)
			throw new BookingException(format("The place %s already booked the user %d on %s", booked.getPlaceNumber(), booked.getOccupiedBy()+"@amdm.fr", date));
		else{
			// TODO Tester que l'user n'a pas déjà reservé de place pour cette date
			List<Place> lesPlacesReserveeParLeUserACetteDate = placedao.findPlacesByCriterions(Restrictions.eq("occupationDate", date), Restrictions.eq("mailOccupant",user.getEmailAMDM()));
			boolean asDejaReserveUneplace = (lesPlacesReserveeParLeUserACetteDate!=null && lesPlacesReserveeParLeUserACetteDate.size()>1);
			if(asDejaReserveUneplace) 
				throw new BookingException(format("The user %s already booked the place %d on %s", booked.getOccupiedBy()+"@amdm.fr", booked.getPlaceNumber(), date));
			else {
			// la place n'est pas pas réservée
			booked.setOccupiedBy(user.getEmailAMDM());
			booked = placedao.persist(booked);
			}
		}

			
		return booked;
	}

	public List<Place> getReservationsOrRelease(User user) throws SQLException, URISyntaxException {
		Assert.notNull(user);
		Assert.notNull(user.getEmailAMDM());
		List<Place> listRetour = new ArrayList<Place>();
		if(user.getPlaceNumber() == null){
			listRetour = placedao.findAllBookedPlaceByUser(user.getEmailAMDM());
		}else{
			listRetour = placedao.findReleaseHistoryByPlace(user.getPlaceNumber());
			List<Place> listPourAffichage = new ArrayList<Place>();
			for (Iterator<Place> iterator = listRetour.iterator(); iterator.hasNext();) {
				Place place = (Place) iterator.next();
				if(place.getOccupiedBy() == null){
					place.setOccupiedBy(" ");
				}
				listPourAffichage.add(place);
			}
			listRetour.clear();
			listRetour.addAll(listPourAffichage);
		}
		return listRetour;
	}
}
