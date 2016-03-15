package org.friends.app.model;

import java.util.Calendar;
import java.util.Date;

import org.friends.app.Configuration;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Fait la jointure entre un user et une session sur un device
 * 
 * @author michael
 */
public class Session {
	
	Integer userId;
	String cookie;
	Date creationDate;
	Date expirationDate;

	public Session(User user) {
		userId = user.getId();
		Calendar cal = Calendar.getInstance();
		creationDate = cal.getTime();
		cookie = Hashing.sha1().hashString(user.getEmailAMDM() + creationDate.toString(), Charsets.UTF_8).toString();
		
		cal.add(Calendar.SECOND, Configuration.COOKIE_DURATION);
		expirationDate = cal.getTime();
	}

	public Integer getUserId() {
		return userId;
	}

	public String getCookie() {
		return cookie;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	@Override
	public String toString() {
		return String.format("[Session : userId=%d, cookie=%s, creation=%3$tb %3$te %3$tY, expiration=%3$tb %3$te %3$tY]", userId, cookie, creationDate, expirationDate);
	}
}
