package org.friends.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	private static final String DATE_PATTERN = "yyyy-MM-dd";

	public static String dateAsString(LocalDate date) {
		return getFormatter().format(date);
	}

	public static LocalDate stringToDate(String maDate) {
		return LocalDate.parse(maDate, getFormatter());
	}

	private static DateTimeFormatter getFormatter() {
		return DateTimeFormatter.ofPattern(DATE_PATTERN);
	}

}
