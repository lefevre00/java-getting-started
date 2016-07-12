package org.friends.app.service.impl;

import static java.lang.String.format;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.BookingException;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UnshareException;
import org.friends.app.util.DateUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spark.utils.Assert;
import spark.utils.StringUtils;

@Service
public class PlaceServiceBean implements PlaceService {

	@Autowired
	private PlaceDao placedao;

	@Override
	public List<Place> getAvailablesAtDate(LocalDate date) {
		return placedao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", DateUtil.dateToString(date)),
				Restrictions.isNull("usedBy"));
	}

	/**
	 * Release a place at date
	 * 
	 * @param placeNumber
	 * @param dateReservation
	 */
	@Override
	public boolean sharePlaces(User user, LocalDate dateDebut, LocalDate dateFin, String emailOccupant) {
		Assert.notNull(user);
		Assert.notNull(dateDebut);
		Assert.notNull(dateFin);
		List<String> listeDates = getDaysBetweenDates(user.getPlaceNumber(), dateDebut, dateFin);
		if (!listeDates.isEmpty()) {
			for (Iterator<String> iterator = listeDates.iterator(); iterator.hasNext();) {
				String leJour = iterator.next();
				if (StringUtils.isEmpty(emailOccupant)){
					placedao.persist(new Place(user.getPlaceNumber().intValue(), leJour));
				}
				else{
					placedao.persist(new Place(user.getPlaceNumber().intValue(), emailOccupant, leJour));
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Book a place for a user, on the given date.
	 * 
	 * @param date,
	 *            when the user need the place
	 * @param user,
	 *            indicate who need the place
	 * @param placeNumber,
	 *            place asked, could be null. If so, we provide the first
	 *            available place.
	 * @return A place occupied by the user, if one is available.
	 * @throws BookingException
	 *             When a user try to book while he already booked a place.
	 */
	@Override
	public Place book(String date, User user, String placeNumber) throws BookingException {

		Assert.notNull(date);
		Assert.notNull(placeNumber);
		Assert.notNull(user);

		List<Place> listPlaceReserve = placedao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", date),
				Restrictions.eq("id.placeNumber", Integer.valueOf(placeNumber)));

		Place booked = (listPlaceReserve != null && listPlaceReserve.size() > 0) ? listPlaceReserve.get(0) : null;// !placedao.findPlaceisFreeAtTheDate(Integer.valueOf(placeNumber),dateAsDate);
		if (booked == null)
			throw new BookingException(format("The place %s can't be booked by the user %s on %s", placeNumber,
					user.getEmailAMDM(), date));

		if (booked.getUsedBy() != null)
			throw new BookingException(format("The place %s already booked the user %d on %s", booked.getPlaceNumber(),
					booked.getUsedBy() + "@amdm.fr", date));
		else {
			// TODO Tester que l'user n'a pas déjà réservé de place pour cette
			// date
			List<Place> lesPlacesReserveeParLeUserACetteDate = placedao.findPlacesByCriterions(
					Restrictions.eq("id.occupationDate", date), Restrictions.eq("usedBy", user.getEmailAMDM()));
			boolean asDejaReserveUneplace = (lesPlacesReserveeParLeUserACetteDate != null
					&& lesPlacesReserveeParLeUserACetteDate.size() >= 1);
			if (asDejaReserveUneplace)
				throw new BookingException(format("The user %s already booked the place %d on %s",
						booked.getUsedBy() + "@amdm.fr", booked.getPlaceNumber(), date));
			else {
				// la place n'est pas pas réservée
				booked.setUsedBy(user.getEmailAMDM());
				placedao.update(booked);
				// booked = placedao.persist(booked);
			}
		}

		return booked;
	}

	/**
	 * Recherche places réservées par :
	 * <li>un utilisateur sans place attribuée, pour les jours j et j+1
	 * 
	 */
	@Override
	public List<Place> getShared(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getEmailAMDM());
		List<Place> listRetour = new ArrayList<Place>();

		LocalDate today = DateUtil.now();

		// utilisateur sans place attribuée
		if (user.getPlaceNumber() == null) {
			// FIXME : attention cette partie du service ne devrait plus etre
			// appelée, seulement celle avec [user.getPlaceNumber != null]
			List<Place> places = new ArrayList<>();

			// Recherche réservation pour le jour j
			places = placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
					Restrictions.ge("id.occupationDate", DateUtil.dateToString(today)));
			if (!places.isEmpty()) {
				listRetour.add(places.get(0));
			}

			// Recherche réservation pour le jour j+1
			places = placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
					Restrictions.ge("id.occupationDate", DateUtil.dateToString(DateUtil.now().plusDays(1))));
			if (!places.isEmpty()) {
				listRetour.add(places.get(0));
			}

		} else {
			listRetour = placedao.findPlacesByCriterions(Restrictions.eq("id.placeNumber", user.getPlaceNumber()),
					Restrictions.ge("id.occupationDate", DateUtil.dateToString(today)));
			List<Place> listPourAffichage = new ArrayList<Place>();
			for (Iterator<Place> iterator = listRetour.iterator(); iterator.hasNext();) {
				Place place = iterator.next();
				if (place.getUsedBy() == null) {
					place.setUsedBy(" ");
				}
				listPourAffichage.add(place);
			}
			listRetour.clear();
			listRetour.addAll(listPourAffichage);
		}
		return listRetour;
	}

	/**
	 * Retourne la place réservée par un utilisateur à une date donnée
	 * 
	 * @param user
	 *            L'utilisateur
	 * @param dateRecherche
	 *            La date de la recherche
	 * @return null si aucune reservation pour ce jour
	 */
	@Override
	public Place getBookedPlaceByUserAtDate(User user, LocalDate dateRecherche) {
		Assert.notNull(user);
		Assert.notNull(dateRecherche);
		Assert.notNull(user.getEmailAMDM());
		List<Place> places = new ArrayList<>();
		// Recherche réservation pour le jour j
		places = placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
				Restrictions.eq("id.occupationDate", DateUtil.dateToString(dateRecherche)));
		return places.size() == 0 ? null : places.get(0);
	}

	/**
	 * Annulation d'une réservation d'un utilisateur ayant réservé ou libéré une
	 * place
	 * 
	 * @param date
	 * @param user
	 * @throws UnshareException
	 */
	@Override
	public void unshare(User user, String unshareDate) throws UnshareException {
		Assert.notNull(user);
		Assert.notNull(unshareDate);
		Assert.notNull(user.getPlaceNumber());
		Place place = isPlaceShared(user.getPlaceNumber(), unshareDate);
		if (!place.isFree()) {
			throw new UnshareException();
		}
		placedao.delete(unshareDate, user.getPlaceNumber());
	}

	@Override
	public void release(User user, String dateToRelease) {
		Assert.notNull(user);
		Assert.isTrue(StringUtils.isNotEmpty(dateToRelease), "date should not be empty");
		Place place = placedao.findPlaceByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
				Restrictions.eq("id.occupationDate", dateToRelease));
		place.setUsedBy(null);
		placedao.update(place);
	}

	@Override
	public List<Place> getReservations(User user) {
		return placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
				Restrictions.ge("id.occupationDate", DateUtil.dateToString(DateUtil.now())));
	}

	/*
	 * Retourne la liste des dates entre 2 dates
	 */
	private List<String> getDaysBetweenDates(Integer placeNumber, LocalDate startdate, LocalDate enddate) {
		List<String> dates = new ArrayList<String>();
		LocalDate dateToAdd = startdate;

		while (dateToAdd.isBefore(enddate.plusDays(1))) {
			if ((DayOfWeek.SATURDAY.equals(dateToAdd.getDayOfWeek()))
					|| (DayOfWeek.SUNDAY.equals(dateToAdd.getDayOfWeek()))) {

			} else {
				// Si place non partagée, on l'ajoute dans la liste
				String dateAsString = DateUtil.dateToString(dateToAdd);
				if (isPlaceShared(placeNumber, dateAsString) == null) {
					dates.add(dateAsString);
				}
			}
			dateToAdd = dateToAdd.plusDays(1);
		}
		return dates;
	}

	@Override
	public Place isPlaceShared(Integer placeNumber, String date) {
		return placedao.findPlaceByCriterions(Restrictions.eq("id.occupationDate", date),
				Restrictions.eq("id.placeNumber", placeNumber));
	}

	@Override
	public boolean canBook(User user, String day) {
		Assert.notNull(user);
		Assert.notNull(day);

		boolean hasNoReservation = placedao.findPlaceByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
				Restrictions.eq("id.occupationDate", day)) == null;

		// Si aucune reservation pour ce user at cette date, et que le user ne
		// peut que reserver une place, alors oui
		if (user.getPlaceNumber() == null)
			return hasNoReservation;

		// L'utilisateur avec place attitrée a-t-il partagé sa place pour le
		// jour demandé.
		Place placePartagee = placedao.findPlaceByCriterions(Restrictions.eq("id.placeNumber", user.getPlaceNumber()),
				Restrictions.eq("id.occupationDate", day));
		return hasNoReservation && placePartagee != null && !placePartagee.isFree();
	}
	
	@Override
	public List<Place> getAllPlaceBetweenTwoDates(String beginDate, String endDate) {
		Assert.notNull(beginDate);
		Assert.notNull(endDate);
		List<Place> listRetour = new ArrayList<Place>();
		listRetour = placedao.findPlacesByCriterions(Restrictions.ge("id.occupationDate", beginDate), Restrictions.le("id.occupationDate", endDate));
		List<Place> listPourAffichage = new ArrayList<Place>();
		for (Iterator<Place> iterator = listRetour.iterator(); iterator.hasNext();) {
			Place place = iterator.next();
			if (place.getUsedBy() == null) {
				place.setUsedBy(" ");
			}
			listPourAffichage.add(place);
		}
		listRetour.clear();
		listRetour.addAll(listPourAffichage);
		return listRetour;
	}

	@Override
	public List<Place> getAllSharedDatesByUser(Integer placeNumber) {
		Assert.notNull(placeNumber);
		
		List<Place> listRetour = new ArrayList<Place>();
		listRetour = placedao.findPlacesByCriterions(Restrictions.ge("id.placeNumber", placeNumber));
		List<Place> listPourAffichage = new ArrayList<Place>();
		for (Iterator<Place> iterator = listRetour.iterator(); iterator.hasNext();) {
			Place place = iterator.next();
			if (place.getUsedBy() == null) {
				place.setUsedBy(" ");
			}
			listPourAffichage.add(place);
		}
		listRetour.clear();
		listRetour.addAll(listPourAffichage);
		return listRetour;		
	}

	@Override
	public List<Place> getAllPlacesBookedByUser(String email) {
		Assert.notNull(email);
		return placedao.findPlacesByCriterions(Restrictions.ge("usedBy", email));			
	}
}
