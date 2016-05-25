package org.friends.app.service.impl;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.ValidationException;

import org.friends.app.dao.SessionDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.hibernate.criterion.Restrictions;
import org.omg.CORBA.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spark.utils.Assert;
import spark.utils.StringUtils;

@Service
public class UserServiceBean implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private MailServiceBean mailService;
	@Autowired
	private PlaceService placeService;

	/**
	 * Authentification de l'utilisateur
	 * 
	 * @param email
	 *            email AMDM
	 * @param pwd
	 *            mot de passe
	 * @return
	 * @throws Exception
	 */
	@Override
	public User authenticate(String email, String pwd) throws Exception {

		parametersValidator(email, pwd);

		User user = findUserByEmail(email);

		if (user == null)
			throw new Exception(USER_UNKNOWN);

		if (user.getTokenMail() != null)
			throw new Exception(USER_DISABLE);

		if (user != null && !pwd.equals(user.getPwd()))
			throw new Exception(PWD_ERROR);

		return user;
	}

	@Override
	public User findUserByEmail(String email) {
		Assert.notNull(email);
		return userDao.findUserByCriterions(Restrictions.eq("emailAMDM", email));
	}

	@Override
	public User findUserByCookie(String cookie) {
		Session session = sessionDao.findByCookie(cookie);
		if (session == null)
			return null;
		return userDao.findById(session.getUserId());
	}

	@Override
	public Session createSession(User user) {
		Assert.notNull(user);
		cleanExpiredSession();
		return sessionDao.persist(new Session(user));
	}

	private void cleanExpiredSession() {
		sessionDao.deleteExpired();
	}

	/**
	 * Création d'un utilisateur. Voici les règles a respecter lors de la
	 * création :
	 * <ul>
	 * <li>Un utilisateur doit comporter un email</li>
	 * <li>Un utilisateur doit avoir un mot de passe</li>
	 * <li>Aucun utilisateur ne comporte le même email</li>
	 * </ul>
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public User create(User user, String applicationUrl) throws Exception {
		Assert.notNull(user);
		Assert.notNull(user.getEmailAMDM());
		Assert.notNull(user.getPwd());

		// Email validator
		if (!emailAMDMValidator(user.getEmailAMDM()))
			throw new Exception("L'email saisi est incorrect !");

		user.setTokenMail(UUID.randomUUID().toString());
		User back = userDao.persist(user);

		mailService.sendWelcome(back, applicationUrl);
		return back;
	}

	@Override
	public User changePlace(User user, Integer place) throws DataIntegrityException {
		Assert.notNull(place);
		Assert.notNull(user);
		Assert.notNull(user.getId()); // User must already have an id

		// Check place change
		// User could not take the place of another existing user
		User userInDb = userDao.findById(user.getId());
		if (userInDb == null)
			throw new DataIntegrityException(USER_UNKNOWN);

		if (!place.equals(userInDb.getPlaceNumber())) {
			User userWithPlace = userDao.findUserByCriterions(Restrictions.eq("placeNumber", place));
			if (userWithPlace != null) {
				throw new DataIntegrityException(PLACE_ALREADY_USED);
			}
		}

		user.setPlaceNumber(place);
		return userDao.persist(user);
	}

	public void reset(User user) {
	}

	/**
	 * On valide le format de l'email saisi qui doit être celui de l'AMDM
	 * L'email doit avoir le format prenom.nom@amdm.fr (un '-' dans le prénom et
	 * le nom sont possibles)
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailAMDMValidator(final String email) {

		String EMAIL_PATTERN = "^([A-Za-z]+\\-?)+\\.([A-Za-z]+\\-?)+@amdm.fr$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public void parametersValidator(String email, String pwd) throws Exception {

		if (StringUtils.isEmpty(email))
			throw new Exception(EMAIL_REQUIRED);

		if (!emailAMDMValidator(email))
			throw new Exception(EMAIL_ERROR);

		if (StringUtils.isEmpty(pwd))
			throw new Exception(PWD_REQUIRED);

	}

	@Override
	public boolean activate(String token) {
		Assert.notNull(token);

		User user = userDao.findUserByCriterions(Restrictions.eq("tokenMail", token));

		boolean success = false;
		if (user != null) {
			success = true;
			user.setTokenMail(null);
			userDao.persist(user);
		}

		return success;
	}

	public void resetPassword(String email, String appUrl) throws Exception {
		if (StringUtils.isEmpty(email))
			throw new IllegalArgumentException("Email required");

		User user = findUserByEmail(email);
		if (user == null)
			throw new ValidationException(EMAIL_UNKNOWN);

		user.setTokenPwd(UUID.nameUUIDFromBytes(user.getEmailAMDM().getBytes()).toString());
		user = userDao.persist(user);

		mailService.sendLostPassword(user, appUrl);
	}

	@Override
	public boolean setPassword(String email, String token, String hash) {
		if (StringUtils.isEmpty(email))
			throw new IllegalArgumentException("Email required");
		if (StringUtils.isEmpty(token))
			throw new IllegalArgumentException("token required");
		if (StringUtils.isEmpty(hash))
			throw new IllegalArgumentException("Hashed password required");

		User user = userDao.findUserByCriterions(Restrictions.eq("emailAMDM", email),
				Restrictions.eq("tokenPwd", token));

		if (user != null) {
			user.setTokenPwd(null);
			user.setPwd(hash);
			userDao.persist(user);
			return true;
		}

		return false;
	}

	@Override
	public void delete(User user) throws UnknownUserException, DataIntegrityException {
		Assert.notNull(user);

		User userDb = userDao.findById(user.getId());
		if (null == userDb) {
			throw new UnknownUserException();
		}

		// Check remaining shared places
		if (null != user.getPlaceNumber()) {
			boolean someMorePlaces = !placeService.getShared(user).isEmpty();
			if (someMorePlaces) {
				throw new DataIntegrityException(USER_DELETE_SHARE);
			}
		}

		// Finaly delete user
		userDao.delete(userDb.getId());
	}
}
