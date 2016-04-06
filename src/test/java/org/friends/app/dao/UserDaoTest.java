package org.friends.app.dao;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.friends.app.HibernateUtil;
import org.friends.app.ParkingTest;
import org.friends.app.model.User;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class UserDaoTest extends ParkingTest {
	

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
	static LocalDate timePoint = LocalDate.now();
	static String strDateToday  = timePoint.format(formatter);
	static String strTomorrow = timePoint.plusDays(1).format(formatter);
	static String strApresDemain = timePoint.plusDays(2).format(formatter);
	static String strYearsteday = timePoint.minusDays(1).format(formatter);
	
	
	private UserDao userDao = new UserDao();
    
    private static String MAIL_RESERVANT = "damien.urvoix@amdm.fr";
    
    @BeforeClass
    public static void beforeClass() throws SQLException {
    	HibernateUtil.getSession();
    }
    
    @Before
    public void createDatabase() throws SQLException, URISyntaxException {
    	init();
    }
    
    @After
    public void clearDataBase() throws SQLException {
    	userDao.clearAllUsers();
    }
    
    @AfterClass
    public static void close(){
    	HibernateUtil.closeSession();
    }

	private void init() throws SQLException, URISyntaxException {

		userDao = new UserDao();
		userDao.persist(new User("abdel.tamditi@amdm.fr", "at" , 133));
		userDao.persist(new User("william.verdeil@amdm.fr", "wv", 141));
		User mick = new User("michael.lefevre@amdm.fr", "ml", 87);
		mick.setTokenMail("mick");
		userDao.persist(mick);
		userDao.persist(new User("damien.urvoix@amdm.fr", "du"));
		User jpc = new User("jean-pierre.cluzel@amdm.fr", "jpc");
		jpc.setTokenPwd("passjpc");
		userDao.persist(jpc);
	}
	
	@Test
	public void findByEmail() throws SQLException, URISyntaxException{
		
    	User damien = userDao.findUserByCriterions(Restrictions.eq("emailAMDM", MAIL_RESERVANT));
    	Assert.assertNotNull("Damien devrait être trouvé", damien);
	}
	
	
    @Ignore
    public void testMethodesDAO() throws SQLException, URISyntaxException {
    	init();
    	User damien = userDao.findUserByEmail(MAIL_RESERVANT);
    	Assert.assertNotNull("Damien devrait être trouvé", damien);
    	
    	User gerard = userDao.findUserByEmail("gerard.mambu@amdm.fr");
    	Assert.assertNull("Gérard n'est pas à la Mutuelle", gerard);
    	
    	User mick =  userDao.findByTokenMail("mick");
    	Assert.assertNotNull("Le token Mail Mick est ok", mick);
    	Assert.assertEquals("Mick a la place 87", Integer.valueOf(87), mick.getPlaceNumber());
    	Assert.assertEquals("Mick a le mot de passe : ", "ml", mick.getPwd());
    	
    	User jp =  userDao.findUserByEmail("jean-pierre.cluzel@amdm.fr");
    	Assert.assertNotNull("Jp  devrait être trouvé", jp);
    	Assert.assertEquals("Le token Password de jpc doit être correct", "passjpc", jp.getTokenPwd());    	
    }
 
}
