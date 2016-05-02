package org.friends.app.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

	private static final String SHORT_PATTERN_EN = "yyyy-MM-dd";
	private static final String SHORT_PATTERN_FR = "dd/MM/yyyy";
	private static final String MEDIUM_PATTERN = "EE dd/MM/yyyy";
	private static final String FULL_PATTERN = "EEEE dd/MM/yyyy";

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
	
	public static String dateToMediumString(LocalDate date) {
		return getMediumFormatter().format(date);
	}

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
	
	private static DateTimeFormatter getMediumFormatter() {
		return DateTimeFormatter.ofPattern(MEDIUM_PATTERN).withLocale(Locale.FRANCE);
	}
	
	public static LocalDate rechercherDateLejourSuivant(LocalDate dateRecherche) {
		if(DayOfWeek.FRIDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.plusDays(3);
		}else if(DayOfWeek.SATURDAY.equals(dateRecherche.getDayOfWeek())){
			dateRecherche = dateRecherche.plusDays(2);
		}else{
			dateRecherche = dateRecherche.plusDays(1);
		}	
		return dateRecherche;
	}
	
	public static String rechercherStrLejourSuivant(LocalDate dateRecherche) {
		return DateUtil.dateToString(rechercherDateLejourSuivant(dateRecherche));
	}
	
	public static boolean isWeekEnd(LocalDate dateRecherche){
		return DayOfWeek.SATURDAY.equals(dateRecherche.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(dateRecherche.getDayOfWeek());
	}
	
}
