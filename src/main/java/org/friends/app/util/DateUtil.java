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
