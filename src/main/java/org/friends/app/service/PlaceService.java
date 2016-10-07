/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.friends.app.service;

import java.time.LocalDate;
import java.util.List;

import org.friends.app.model.Place;
import org.friends.app.model.User;

public interface PlaceService {

	/**
	 * Retourne les places disponibles pour une date donnée
	 * 
	 * @param date
	 * @return
	 */
	public List<Place> getAvailablesAtDate(LocalDate date);

	/**
	 * Book a place for a user, on the given date.
	 * 
	 * @param date, when the user need the place
	 * @param user, indicate who need the place
	 * @param placeNumber, place asked, could be null. If so, we provide the first available place.
	 * @return A place occupied by the user, if one is available.
	 * @throws BookingException When a user try to book while he already booked a place.
	 */	
	public Place book(String date, User user, String placeNumber) throws BookingException;

	/**
	 * Retourne toutes les places partagées par un utilisateurs qui sont disponibles à partir de la date du jour 
	 * 
	 */	
	public List<Place> getShared(User user);

	/**
	 * Annulation du partage d'une place à une date donnée, uniquement si place non réservée par un autre utilisateur
	 * 
	 * @param date
	 * @param user
	 * @throws UnshareException
	 */	
	void unshare(User user, String date) throws UnshareException;

	/**
	 * User libère sa place entre 2 dates avec ou sans désignation de l'occupant
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
	 * Retourne toutes les places réservées par l'utilisateur à partir de la date du jour
	 * 
	 * @param user
	 * @return The list of places booked, or empty list if none.
	 */
	public List<Place> getReservations(User user);

	/***
	 * On vérifie à une date donnée, si le user n'a pas réservé de place et s'il a partagé sa place, cette dernière est occupée 
	 * 
	 * @param user
	 * @param day
	 * @return retourne true si l'utilisateur peut réserver une place sinon retourne false
	 */
	public boolean canBook(User user, String day);

	/**
	 * Recherche si une place est partagée à une date donnée
	 * 
	 * @param placeNumber
	 * @param dateToString
	 * @return place
	 */
	public Place isPlaceShared(Integer placeNumber, String dateToString);

	
	/**
	 * Retourne la place réservée par un utilisateur à une date donnée
	 * 
	 * @param user	L'utilisateur
	 * @param dateRecherche		La date de la recherche
	 * @return null si aucune reservation pour ce jour
	 */	
	public Place getBookedPlaceByUserAtDate(User user, LocalDate dateRecherche);

	/**
	 * Retourne toutes les places disponibles entre deux dates
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Place> getAllPlaceBetweenTwoDates(String beginDate, String endDate);
	
	/**
	 * Retourne toutes les dates de partage d'une place restée inoccupée 
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
