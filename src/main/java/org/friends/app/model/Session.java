package org.friends.app.model;

import java.util.Date;

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

	public Session(User user) {
		userId = user.getIdUser();
		creationDate = new Date();
		cookie = Hashing.sha1().hashString(user.getEmailAMDM() + creationDate.toString(), Charsets.UTF_8).toString();
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
	
	@Override
	public String toString() {
		return String.format("[Session : userId=%d, cookie=%s, creation=%3$tb %3$te %3$tY]", userId, cookie, creationDate);
	}
}
