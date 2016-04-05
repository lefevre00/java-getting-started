package org.friends.app.service.impl;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.ValidationException;

import org.friends.app.dao.SessionDao;
import org.friends.app.dao.UserDao;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.hibernate.criterion.Restrictions;

import spark.utils.Assert;
import spark.utils.StringUtils;


public class UserServiceBean implements UserService{
	
	UserDao userDao = new UserDao();
	SessionDao sessionDao = new SessionDao();
	MailServiceBean mailService = new MailServiceBean();
	
	/**
	 * Authentification de l'utilisateur
	 * 
	 * @param email email AMDM
	 * @param pwd mot de passe
	 * @return
	 * @throws Exception
	 */
	@Override
	public User userAuthentication (String email, String pwd) throws Exception{
		
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
	public User findUserByEmail(String email) throws SQLException, URISyntaxException {
		Assert.notNull(email);
		return userDao.findUserByEmail(email);
		//return userDao.findFirst(user -> user.getEmailAMDM().equals(email));
	}

	@Override
	public User findUserByCookie(String cookie) throws SQLException, URISyntaxException {
		Session session = sessionDao.findByCookie(cookie);
		if (session == null)
			return null;
		return userDao.findById(session.getUserId()); 
	}
	
	public Session createSession(User user) throws SQLException, URISyntaxException {
		Assert.notNull(user);
		cleanExpiredSession();
		return sessionDao.persist(new Session(user));
	}
	
	private void cleanExpiredSession() throws SQLException, URISyntaxException {
		sessionDao.deleteExpired();
	}

	/**
	 * Création d'un utilisateur.
	 * Voici les règles a respecter lors de la création :
	 * <ul>
	 *   <li>Un utilisateur doit comporter un email</li>
	 *   <li>Un utilisateur doit avoir un mot de passe</li>
	 *   <li>Aucun utilisateur ne comporte le même email</li>
	 * </ul>
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
	
	public User update(User user) throws SQLException, URISyntaxException {
		Assert.notNull(user);
		Assert.notNull(user.getId()); // User must already have an id
		return userDao.persist(user);
	}
	
	
	public void reset(User user) {}
	
	/**
	 * On valide le format de l'email saisi qui doit être celui de l'AMDM
	 * L'email doit avoir le format prenom.nom@amdm.fr (un '-' dans le prénom et le nom sont possibles)
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailAMDMValidator(final String email){

		String EMAIL_PATTERN = "^([A-Za-z]+\\-?)+\\.([A-Za-z]+\\-?)+@amdm.fr$";
	    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	    
	    Matcher matcher = pattern.matcher(email);
        return matcher.matches();
		
	}	
	
	
	public void parametersValidator(String email, String pwd) throws Exception {
		
		if (StringUtils.isEmpty(email)) 
			throw new Exception(EMAIL_REQUIRED);
		
		if(!emailAMDMValidator(email)) 
			throw new Exception(EMAIL_ERROR);		
		
		if(StringUtils.isEmpty(pwd)) 
			throw new Exception(PWD_REQUIRED);

	}

	public boolean activate(String token) throws SQLException, URISyntaxException {
		Assert.notNull(token);
		
		User user = userDao.findByTokenMail(token);
		
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

	public boolean setPassword(String email, String token, String hash) throws SQLException, URISyntaxException {
		if (StringUtils.isEmpty(email))
			throw new IllegalArgumentException("Email required");
		if (StringUtils.isEmpty(token))
			throw new IllegalArgumentException("token required");
		if (StringUtils.isEmpty(hash))
			throw new IllegalArgumentException("Hashed password required");
		
		User user = userDao.findUserByCriterions(Restrictions.eq("email", email), Restrictions.eq("tokenPassword", token));

		if (user != null) {
			user.setTokenPwd(null);
			user.setPwd(hash);
			userDao.persist(user);
			return true;
		}
		
		return false;
	}
}
