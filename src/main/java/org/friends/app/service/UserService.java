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
package org.friends.app.service;

import java.util.List;

import org.friends.app.model.UserSession;
import org.friends.app.model.User;
import org.omg.CORBA.UnknownUserException;

public interface UserService {

	public static final String EMAIL_ERROR = "email.error";
	public static final String EMAIL_REQUIRED = "email.required";
	public static final String EMAIL_UNKNOWN = "email.unknown";

	public static final String PWD_ERROR = "password.error";
	public static final String PWD_REQUIRED = "password.required";

	public static final String USER_DISABLE = "user.disable";
	public static final String USER_UNKNOWN = "user.unknown";
	public static final String USER_EXISTE = "user.existe";
	public static final String USER_DELETE_SHARE = "user.delete.shared";
	public static final String USER_DELETE_BOOK = "user.delete.booked";
	public static final String USER_DELETE_USED = "user.delete.used";

	public static final String PLACE_ALREADY_USED = "user.place.used";
	public static final String VALIDATION_TOKEN_ERROR = "user.token.error";

	public User authenticate(String email, String pwd) throws Exception;

	public User findUserByEmail(String email);

	public User findUserByCookie(String cookie);

	public User create(User user, String applicationHost) throws Exception;

	public UserSession createSession(User authUser);

	public boolean setPassword(String email, String token, String mdp);

	public User changePlace(User user, Integer place) throws DataIntegrityException;

	public boolean activate(String token);

	/**
	 * Delete a user. If there is coming shared places, if any.
	 * 
	 * @param user
	 * @return true if removed.
	 * @throws UnknownUserException
	 * @throws DataIntegrityException
	 */
	public void delete(User user) throws UnknownUserException, DataIntegrityException;

	public void resetPassword(String email, String appUrl) throws Exception;

	public List<User> getAllUser();
	
	public List<User> getAllUsersWithoutPlaces();
	
	public List<User> getAllUsersWithoutAdmin();
	
	public boolean changePassword(String email, String pwd);
	
	public boolean updateUser(Integer idUser, String email, String mobile, Integer placeNumber, boolean mailInformation);

	/**
	 * retourne l'utilisateur ayant un numéro de place
	 * @param placeNumber
	 * @return
	 */
	public User findUserByPlaceNUmber(Integer placeNumber);

	/**
	 * permet de modifier l'utilisateur pré-créer par l'admin et ce dans le cas d'une utilisation limitée.
	 * @param userExiste
	 * @param pwd
	 * @param appUrl
	 * @throws Exception
	 */
	public void updateInscriptionUser(User userExiste, String pwd, String appUrl) throws Exception ;

	public boolean findUserByEmailAndToken(String email, String tokenMail);
}
