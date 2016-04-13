package org.friends.app.service.impl;

import static java.lang.String.format;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.model.User;
import org.friends.app.service.PlaceService;
import org.friends.app.util.DateUtil;
import org.hibernate.criterion.Restrictions;

import spark.utils.Assert;

public class PlaceServiceBean implements PlaceService {

	PlaceDao placedao = new PlaceDao();

	public List<Place> getAvailableByDate(LocalDate date) {
		return placedao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", DateUtil.dateToString(date)),
				Restrictions.isNull("mailOccupant"));
	}
	/**
	 * dqsdqsd
	 * Release a place at date
	 * 
	 * @param numberPlace
	 * @param dateReservation
	 * @throws SQLException
	 * @throws URISyntaxException
	 * @throws BookingException
	 */
	public void releasePlace(Integer numberPlace, LocalDate dateReservation) throws BookingException {
		Assert.notNull(numberPlace);
		Assert.notNull(dateReservation);
		String dateAsString = DateUtil.dateToString(dateReservation);
		List<Place> listPlaceReserve = placedao.findPlacesByCriterions(
				Restrictions.eq("id.occupationDate", dateAsString),
				Restrictions.eq("id.placeNumber", numberPlace));
		if (listPlaceReserve != null && listPlaceReserve.size() > 0) {
			throw new BookingException(
					format("The place %s can't be book on %s", numberPlace.intValue(), dateReservation));
		} else {
			placedao.persist(new Place(numberPlace.intValue(), dateAsString));
		}
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

		if (booked.getOccupiedBy() != null)
			throw new BookingException(format("The place %s already booked the user %d on %s", booked.getPlaceNumber(), booked.getOccupiedBy()+"@amdm.fr", date));
		else{
			// TODO Tester que l'user n'a pas d�j� reserv� de place pour cette date
			List<Place> lesPlacesReserveeParLeUserACetteDate = placedao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", date), Restrictions.eq("mailOccupant",user.getEmailAMDM()));
			boolean asDejaReserveUneplace = (lesPlacesReserveeParLeUserACetteDate!=null && lesPlacesReserveeParLeUserACetteDate.size()>=1);
			if(asDejaReserveUneplace) 
				throw new BookingException(format("The user %s already booked the place %d on %s", booked.getOccupiedBy()+"@amdm.fr", booked.getPlaceNumber(), date));
			else {
			// la place n'est pas pas r�serv�e
			booked.setOccupiedBy(user.getEmailAMDM());
			placedao.update(booked);
			//booked = placedao.persist(booked);
			}
		}

		return booked;
	}

	/**
	 * Recherche places r�serv�es par :  
	 * <li> un utilisateur sans place attribu�e, pour les jours j et j+1
	 * 
	 */
	public List<Place> getReservationsOrRelease(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getEmailAMDM());
		List<Place> listRetour = new ArrayList<Place>();
		// utilisateur sans place attribuée
		if (user.getPlaceNumber() == null) {
			List<Place> places = new ArrayList<>();
			// Recherche réservation pour le jour j	
			places = placedao.findPlacesByCriterions(Restrictions.eq("mailOccupant", user.getEmailAMDM()),Restrictions.ge("id.occupationDate", DateUtil.dateToString(LocalDate.now())));
			if (!places.isEmpty()){
				listRetour.add(places.get(0));	
			}

			// Recherche réservation pour le jour j+1
			places = placedao.findPlacesByCriterions(Restrictions.eq("mailOccupant", user.getEmailAMDM()),
					Restrictions.ge("id.occupationDate", DateUtil.dateToString(LocalDate.now().plusDays(1))));
			if (!places.isEmpty()) {
				listRetour.add(places.get(0));
			}

		} else {
			listRetour = placedao.findPlacesByCriterions(Restrictions.eq("id.placeNumber", user.getPlaceNumber()));
			List<Place> listPourAffichage = new ArrayList<Place>();
			for (Iterator<Place> iterator = listRetour.iterator(); iterator.hasNext();) {
				Place place = (Place) iterator.next();
				if (place.getOccupiedBy() == null) {
					place.setOccupiedBy(" ");
				}
				listPourAffichage.add(place);
			}
			listRetour.clear();
			listRetour.addAll(listPourAffichage);
		}
		return listRetour;
	}

	/**
	 * Retourne la place d'un user r�serv�e � une date d�finie
	 * sinon retourne null
	 * @param user
	 * @param dateRecherche
	 * @return
	 */
	public Place getPlaceReservedByUserAtTheDate(User user, LocalDate dateRecherche) {
		Assert.notNull(user);
		Assert.notNull(dateRecherche);
		Assert.notNull(user.getEmailAMDM());
		List<Place> places = new ArrayList<>();
		// Recherche réservation pour le jour j	
		places = placedao.findPlacesByCriterions(Restrictions.eq("mailOccupant", user.getEmailAMDM()),Restrictions.eq("id.occupationDate", DateUtil.dateToString(dateRecherche)));
		return places.size() == 0 ? null : places.get(0);
	}
	
	/**
	 * Annulation d'une réservation d'un utilisateur ayant réservé ou libéré une place 
	 * @param date
	 * @param user
	 */
	@Override
	public void unsharePlaceByDate(Place place, User user)  {
		Assert.notNull(place);
		Assert.notNull(user);
		if(user.getPlaceNumber() != null){
			if(place.getOccupiedBy()!=null){
				System.out.println("envoyer un mail à " + place.getOccupiedBy());
			}
			placedao.delete(place.getOccupationDate(), user.getPlaceNumber());
		}else{
			place.setOccupiedBy(null);
			placedao.update(place);
		}
	}

	
}
