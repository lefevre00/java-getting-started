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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * A user of the application.
 * 
 * @author michael lefevre
 */

@Entity
@Table(name = "USERS")
@NamedQueries(value = {
		@NamedQuery(name=User.QUERY_UPDATE_USER, query="update User usr set emailAMDM = :email, placeNumber = :placeNumber where id = :id")
})
public class User {

	public static final String QUERY_UPDATE_USER = "updateUser";
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "EMAIL")
	private String emailAMDM;

	@Column(name = "PLACE_ID")
	private Integer placeNumber;

	@Column(name = "PASSWORD")
	private String pwd;

	@Column(name = "TOKEN_MAIL")
	private String tokenMail;

	@Column(name = "TOKEN_PASSWORD")
	private String tokenPwd;

	@Column(name = "APIKEY")
	private String key;

	public User() {
	}

	public User(String email, String mdp) {
		emailAMDM = email;
		pwd = mdp;
	}

	public User(String email, String mdp, Integer placeNum) {
		emailAMDM = email;
		pwd = mdp;
		placeNumber = placeNum;
	}

	/**
	 * @return the idUser
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param idUser
	 *            the idUser to set
	 */
	public void setId(Integer idUser) {
		this.id = idUser;
	}

	/**
	 * @return the emailAMDM
	 */
	public String getEmailAMDM() {
		return emailAMDM;
	}

	/**
	 * @param emailAMDM
	 *            the emailAMDM to set
	 */
	public void setEmailAMDM(String emailAMDM) {
		this.emailAMDM = emailAMDM;
	}

	/**
	 * @return the placeNumber
	 */
	public Integer getPlaceNumber() {
		return placeNumber;
	}

	/**
	 * @param placeNumber
	 *            the placeNumber to set
	 */
	public void setPlaceNumber(Integer placeNumber) {
		this.placeNumber = placeNumber;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setTokenMail(String token) {
		this.tokenMail = cleanToken(token);
	}

	private String cleanToken(String token) {
		return token != null ? token.replace("-", "") : null;
	}

	public String getTokenMail() {
		return tokenMail;
	}

	public String getTokenPwd() {
		return tokenPwd;
	}

	public void setTokenPwd(String token) {
		this.tokenPwd = cleanToken(token);
	}
}
