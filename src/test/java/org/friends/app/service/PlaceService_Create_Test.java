package org.friends.app.service;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.friends.app.dao.PlaceDao;
import org.friends.app.model.Place;
import org.friends.app.service.impl.BookingException;
import org.friends.app.service.impl.PlaceServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaceService_Create_Test {
	
	static String uneDateAuBonFormat = "2006-03-01";
	static String uneDateAuMauvaisFormat = "pasbien";
	
	@InjectMocks
	PlaceServiceBean service = new PlaceServiceBean();
	
	@Mock PlaceDao dao;
	
	/*
	 * Test avec place nulle
	 */
	@Test(expected=IllegalArgumentException.class)
	public void une_place_ne_doit_pas_etre_nulle() throws SQLException, URISyntaxException, BookingException {
		service.releasePlace(null,null);
	}
	
	/*
	 * Test avec place avec number null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void une_place_doit_avoir_un_id() throws SQLException, URISyntaxException, BookingException {
		Place unePlace = PlaceBuilder.unePlace().build(null, uneDateAuBonFormat);
		service.releasePlace(unePlace.getPlaceNumber(), LocalDate.parse(unePlace.getOccupationDate(), PlaceDao.formatter));
	}
	

	
	/*
	 * Test avec place avec une date nulle
	 */
	@Test(expected=IllegalArgumentException.class)
	public void une_place_doit_avoir_une_date() throws SQLException, URISyntaxException, BookingException {
		Place unePlace = PlaceBuilder.unePlace().build(Integer.valueOf(1), null);
		service.releasePlace(unePlace.getPlaceNumber(),null);
	}
	
	
	/*
	 * Test avec place avec un mauvais format YYYY-mm-dd
	 */
	@Test(expected=DateTimeParseException.class)
	public void une_place_doit_avoir_une_date_valide() throws SQLException, URISyntaxException, BookingException {
		Place unePlace = PlaceBuilder.unePlace().build(Integer.valueOf(1), uneDateAuMauvaisFormat);
		service.releasePlace(unePlace.getPlaceNumber(), LocalDate.parse(unePlace.getOccupationDate(), PlaceDao.formatter));
	}
	
}
