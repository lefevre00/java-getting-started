package org.friends.app.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.friends.app.ConfHelper;
import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Fait la jointure entre un user et une session sur un device
 * 
 * @author michael
 */

@Entity
@Table(name = "SESSIONS")
@NamedQueries(value = {
		@NamedQuery(name = UserSession.DELETE_EXPIRED, query = "delete from UserSession where expirationDate < :date"),
		@NamedQuery(name = UserSession.QUERY_FIND_BY_COOKIE, query = "select s from UserSession s where s.cookie = :cookie"),
		@NamedQuery(name = UserSession.DELETE_BY_USER_ID, query = "delete from UserSession s where s.userId = :user_id") })
public class UserSession {

	public static final String DELETE_EXPIRED = "expired";

	public static final String QUERY_FIND_BY_COOKIE = "findSessionByCookie";
	
	public static final String DELETE_BY_USER_ID = "deleteByUserID";

	@Id
	@GeneratedValue(generator = "incrementS")
	@GenericGenerator(name = "incrementS", strategy = "increment")
	@Column(name = "SESSION_ID")
	Integer sessionId;

	@Column(name = "USER_ID")
	Integer userId;

	@Column(name = "COOKIE")
	String cookie;

	@Column(name = "CREATION_DATE")
	Date creationDate;

	@Column(name = "EXPIRATION_DATE")
	Date expirationDate;

	public UserSession() {
		// pour hibernate
	}

	public UserSession(User user) {
		userId = user.getId();
		Calendar cal = Calendar.getInstance();
		creationDate = cal.getTime();
		cookie = Hashing.sha1().hashString(user.getEmailAMDM() + creationDate.toString(), Charsets.UTF_8).toString();

		cal.add(Calendar.SECOND, ConfHelper.COOKIE_DURATION);
		expirationDate = cal.getTime();
	}

	public Integer getSessionId() {
		return sessionId;
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
		return String.format(
				"[Session : userId=%d, cookie=%s, creation=%3$tb %3$te %3$tY, expiration=%3$tb %3$te %3$tY]", userId,
				cookie, creationDate, expirationDate);
	}
}
