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
package org.friends.app.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

	private static final String SHORT_PATTERN_EN = "yyyy-MM-dd";
	private static final String SHORT_PATTERN_FR = "dd/MM/yyyy";
	private static final String MEDIUM_PATTERN = "EE dd/MM/yyyy";
	private static final String FULL_PATTERN = "EEEE dd/MM/yyyy";
	private static final String DAYTEXT_PATTERN = "EEEE";

	public static ZoneId EUROPE_PARIS  = ZoneId.of("Europe/Paris");

	/**
	 * @param date The date to format
	 * @return A date given following {@link #SHORT_PATTERN_EN} pattern  
	 */
	public static String dateToString(LocalDate date, Locale locale) {
		return getShortFormatter(locale).format(date);
	}
	
	public static String dateToString(LocalDate date) {
		return dateToString(date, null);
	}
	


	public static String dateToFullString(LocalDate date) {
		return getFullFormatter().format(date);
	}
	
	public static String dateToDayString(LocalDate date) {
		return getDayFormatter().format(date);
	}
	
	public static String dateToMediumString(LocalDate date) {
		return getMediumFormatter().format(date);
	}

	/**
	 * Converti une date au format {@link #SHORT_PATTERN_EN} vers une LocalDate
	 * @param maDate
	 * @return
	 */
	public static LocalDate stringToDate(String maDate) {
		return stringToDate(maDate, null);
	}
	
	public static LocalDate stringToDate(String maDate, Locale locale) {
		return LocalDate.parse(maDate, getShortFormatter(locale));
	}

	private static DateTimeFormatter getShortFormatter(Locale locale) {
		String pattern = SHORT_PATTERN_EN;
		if (Locale.FRANCE.equals(locale))
			pattern = SHORT_PATTERN_FR;
		return DateTimeFormatter.ofPattern(pattern);
	}
	
	private static DateTimeFormatter getFullFormatter() {
		return DateTimeFormatter.ofPattern(FULL_PATTERN).withLocale(Locale.FRANCE);
	}
	
	private static DateTimeFormatter getDayFormatter() {
		return DateTimeFormatter.ofPattern(DAYTEXT_PATTERN).withLocale(Locale.FRANCE);
	}
	
	private static DateTimeFormatter getMediumFormatter() {
		return DateTimeFormatter.ofPattern(MEDIUM_PATTERN).withLocale(Locale.FRANCE);
	}
	
	/**
	 * Get localized LocalDate, using Europe / Paris time zone.
	 */
	public static LocalDate now() {
		return LocalDate.now(EUROPE_PARIS);
	}
}
