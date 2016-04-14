package org.friends.app.service.impl;

import static java.lang.String.format;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.DayOfWeek;
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
import spark.utils.StringUtils;

public class PlaceServiceBean implements PlaceService {

	PlaceDao placedao = new PlaceDao();

	public List<Place> getAvailableByDate(LocalDate date) {
		return placedao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", DateUtil.dateToString(date)),
				Restrictions.isNull("usedBy"));
	}
	/**
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
	 * Release a place at date
	 * 
	 * @param placeNumber
	 * @param dateReservation
	 * @throws SQLException
	 * @throws URISyntaxException
	 * @throws BookingException
	 */
	public boolean sharePlaces(User user, LocalDate dateDebut, LocalDate dateFin) throws Exception  {
		Assert.notNull(user);
		Assert.notNull(dateDebut);
		Assert.notNull(dateFin);
		List<String> listeDates = getDaysBetweenDates(user.getPlaceNumber(), dateDebut, dateFin);
		if (!listeDates.isEmpty()){
			for (Iterator<String> iterator = listeDates.iterator(); iterator.hasNext();) {
				String leJour = (String) iterator.next();
				placedao.persist(new Place(user.getPlaceNumber().intValue(), leJour));
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
			throw new BookingException(format("The place %s already booked the user %d on %s", booked.getPlaceNumber(), booked.getUsedBy()+"@amdm.fr", date));
		else{
			// TODO Tester que l'user n'a pas déjà réservé de place pour cette date
			List<Place> lesPlacesReserveeParLeUserACetteDate = placedao.findPlacesByCriterions(Restrictions.eq("id.occupationDate", date), Restrictions.eq("usedBy",user.getEmailAMDM()));
			boolean asDejaReserveUneplace = (lesPlacesReserveeParLeUserACetteDate!=null && lesPlacesReserveeParLeUserACetteDate.size()>=1);
			if(asDejaReserveUneplace) 
				throw new BookingException(format("The user %s already booked the place %d on %s", booked.getUsedBy()+"@amdm.fr", booked.getPlaceNumber(), date));
			else {
			// la place n'est pas pas réservée
			booked.setUsedBy(user.getEmailAMDM());
			placedao.update(booked);
			//booked = placedao.persist(booked);
			}
		}

		return booked;
	}

	/**
	 * Recherche places réservées par :  
	 * <li> un utilisateur sans place attribuée, pour les jours j et j+1
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
			places = placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),Restrictions.ge("id.occupationDate", DateUtil.dateToString(LocalDate.now())));
			if (!places.isEmpty()){
				listRetour.add(places.get(0));	
			}

			// Recherche réservation pour le jour j+1
			places = placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),
					Restrictions.ge("id.occupationDate", DateUtil.dateToString(LocalDate.now().plusDays(1))));
			if (!places.isEmpty()) {
				listRetour.add(places.get(0));
			}

		} else {
			listRetour = placedao.findPlacesByCriterions(Restrictions.eq("id.placeNumber", user.getPlaceNumber()));
			List<Place> listPourAffichage = new ArrayList<Place>();
			for (Iterator<Place> iterator = listRetour.iterator(); iterator.hasNext();) {
				Place place = (Place) iterator.next();
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
	 * Retourne la place d'un user réservée à une date définie
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
		places = placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),Restrictions.eq("id.occupationDate", DateUtil.dateToString(dateRecherche)));
		return places.size() == 0 ? null : places.get(0);
	}
	
	/**
	 * Annulation d'une réservation d'un utilisateur ayant réservé ou libéré une place 
	 * @param date
	 * @param user
	 * @throws UnshareException 
	 */
	@Override
	public void unsharePlaceByDate(User user, String unshareDate) throws UnshareException  {
		Assert.notNull(user);
		Assert.notNull(unshareDate);
		Place place = placedao.findPlaceByCriterions(Restrictions.eq("id.placeNumber", user.getPlaceNumber()),Restrictions.eq("id.occupationDate", unshareDate));
		if (StringUtils.isNotEmpty(place.getUsedBy())) {
			throw new UnshareException();
		}
		placedao.delete(unshareDate, user.getPlaceNumber());
	}
	
	public void release(User user, String dateToRelease) {
		Assert.notNull(user);
		Assert.notNull(dateToRelease);
		Place place = placedao.findPlaceByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()),Restrictions.eq("id.occupationDate", dateToRelease));
		place.setUsedBy(null);
		placedao.update(place);
	}
	
	public List<Place> getReservations(User user) {
		return placedao.findPlacesByCriterions(Restrictions.eq("usedBy", user.getEmailAMDM()));
	}
	
	/*
	 * Retourne la liste des dates entre 2 dates
	 */
	private List<String> getDaysBetweenDates(Integer placeNumber, LocalDate startdate, LocalDate enddate){
	    List<String> dates = new ArrayList<String>();
	    LocalDate dateToAdd = startdate;
	    
	    while (dateToAdd.isBefore(enddate.plusDays(1))){
	    	if((DayOfWeek.SATURDAY.equals(dateToAdd.getDayOfWeek())) || (DayOfWeek.SUNDAY.equals(dateToAdd.getDayOfWeek()))){

	    	}else{
	    		// Si place non partagée, on l'ajoute dans la liste
	    		String dateAsString = DateUtil.dateToString(dateToAdd);
				if ( placedao.findPlaceByCriterions(Restrictions.eq("id.occupationDate", dateAsString), Restrictions.eq("id.placeNumber", placeNumber)) == null){
					dates.add(dateAsString);
				}	    	    		
	        }
	    	dateToAdd= dateToAdd.plusDays(1);
	    }	    
	    return dates;
	}	
}
