package org.friends.app.service.impl;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.ValidationException;

import org.friends.app.dao.SessionDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Place;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.DataIntegrityException;
import org.friends.app.service.MailService;
import org.friends.app.service.PlaceService;
import org.friends.app.service.UserService;
import org.friends.app.validator.EmailValidator;
import org.hibernate.criterion.Restrictions;
import org.omg.CORBA.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import spark.utils.Assert;
import spark.utils.StringUtils;

@Service
public class UserServiceBean implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private MailService mailService;
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
		Assert.isTrue(!Strings.isNullOrEmpty(email), "email null or empty");
		Assert.isTrue(!Strings.isNullOrEmpty(pwd), "password null or empty");

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
		if (!EmailValidator.isValid(user.getEmailAMDM()))
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

	@Override
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
			List<Place> shared = placeService.getShared(user);
			if (!shared.isEmpty()) {
				long nbrUsed = shared.stream().filter(p -> p.getUsedBy() != null).count();
				if (nbrUsed > 0) {
					throw new DataIntegrityException(USER_DELETE_USED);
				}
				throw new DataIntegrityException(USER_DELETE_SHARE);
			}
		}

		// Check remaining booked places
		if (!placeService.getReservations(userDb).isEmpty()) {
			throw new DataIntegrityException(USER_DELETE_BOOK);
		}

		// Finaly delete user
		userDao.delete(userDb.getId());
	}
}
