package org.friends.app.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	private final static String EMAIL_PATTERN = "^([A-Za-z]+\\-?)+\\.([A-Za-z]+\\-?)+@amdm.fr$";

	private final static Pattern pattern;
	static {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public static boolean isValid(String email) {
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
