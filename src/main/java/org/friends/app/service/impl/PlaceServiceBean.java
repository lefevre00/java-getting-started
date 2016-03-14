package org.friends.app.service.impl;

import static java.lang.String.format;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;

import spark.utils.Assert;

public class PlaceServiceBean {
	
	PlaceDao placedao = new PlaceDao();
	
	public List<Place> getAvailableByDate(LocalDate date) {
		return placedao.findAllFreeByDate(date);
	}
	
	public void releasePlace(Integer numberPlace, String dateReservation) {
		Place releasePlace = new Place(numberPlace.intValue(), null, dateReservation);
		placedao.persist(releasePlace);
	}

	/**
	 * Book a place for a user, on the given date.
	 * @param date, when the user need the place
	 * @param user, indicate who need the place
	 * @param placeNumber, place asked, could be null. If so, we provide the first available place.
	 * @return A place occupied by the user, if one is available.
	 * @throws BookingException When a user try to book while he already booked a place.
	 */
	public Place book(String date, User user, String placeNumber) throws BookingException {
		
		Assert.notNull(date);
		Assert.notNull(user);
		
		// Check double booking
		Place place = placedao.findFirst(new Predicate<Place>() {
			@Override
			public boolean test(Place t) {
				return (t.getOccupationDate().equals(date) && t.getOccupiedBy().equals(user.getEmailAMDM()));
			}
		});
		
		if (place != null)
			throw new BookingException(format("The user %s already booked the place %d on %s", user.getEmailAMDM(), place.getPlaceNumber(), date));
		

		LocalDate dateAsDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN));
		List<Place> places = getAvailableByDate(dateAsDate);

		Optional<Place> optPlace = null;
		if (placeNumber != null) {
			optPlace = places.stream().filter(p -> p.getPlaceNumber().toString().equals(placeNumber)).findFirst();
		}
		if (!optPlace.isPresent()) {
			optPlace = places.stream().findAny();
		}
		
		Place booked = null;
		if (optPlace.isPresent()) {
			booked = optPlace.get();
			booked.setOccupiedBy(user.getEmailAMDM());
			booked = placedao.persist(booked);
		}
			
		return booked;
	}
}
