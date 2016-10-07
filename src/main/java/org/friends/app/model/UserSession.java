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
