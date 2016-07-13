package org.friends.app.service;

import java.util.List;

import org.friends.app.model.Session;
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
	public static final String USER_DELETE_SHARE = "user.delete.shared";
	public static final String USER_DELETE_BOOK = "user.delete.booked";
	public static final String USER_DELETE_USED = "user.delete.used";

	public static final String PLACE_ALREADY_USED = "user.place.used";

	public User authenticate(String email, String pwd) throws Exception;

	public User findUserByEmail(String email);

	public User findUserByCookie(String cookie);

	public User create(User user, String applicationHost) throws Exception;

	public Session createSession(User authUser);

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
	
	public boolean changePassword(String email, String pwd);
	
	public boolean updateUser(Integer idUser, String email, String mobile, Integer placeNumber);

	/**
	 * retourne l'utilisateur ayant un numéro de place
	 * @param placeNumber
	 * @return
	 */
	public User findUserByPlaceNUmber(Integer placeNumber);
}
