package org.friends.app.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.friends.app.HibernateUtil;
import org.friends.app.ParkingTest;
import org.friends.app.model.Place;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class PlaceDaoTest extends ParkingTest {
	

	private static String MAIL_RESERVANT = "damien.urvoix@amdm.fr";
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
	static LocalDate timePoint = LocalDate.now();
	static String strDateToday  = timePoint.format(formatter);
	static String strTomorrow = timePoint.plusDays(1).format(formatter);
	static String strApresDemain = timePoint.plusDays(2).format(formatter);
	static String strYearsteday = timePoint.minusDays(1).format(formatter);
	
	private PlaceDao placeDao;
    
    @BeforeClass
    public static void beforeClass() throws SQLException {
    	HibernateUtil.getSession();
    }
    
    @AfterClass
    public static void close(){
    	HibernateUtil.closeSession();
    }
    
    @Before
    public void createDatabase() throws SQLException {
    	placeDao = new PlaceDao();
    	initDb();
    }
    
    @After
    public void clearPlaces() throws SQLException {
    	placeDao.clearAllPlacesBeforeDate(LocalDate.now().plusDays(40));
    }

	private void initDb() {
		placeDao.clearAllPlacesBeforeDate(LocalDate.now().plusDays(40)); // suppresion de toutes les réservations
		placeDao.persist(new Place(new Integer(3), MAIL_RESERVANT, strTomorrow));
		placeDao.persist(new Place(new Integer(1), strDateToday)); //Place libre aujourd'hui free = true
		placeDao.persist(new Place(new Integer(141), strDateToday));
		placeDao.persist(new Place(new Integer(2), MAIL_RESERVANT, strDateToday));//place occupée aujourd'hui
		placeDao.persist(new Place(new Integer(35), MAIL_RESERVANT, strApresDemain));//place occupée aujourd'hui
    	placeDao.persist(new Place(new Integer(34), strYearsteday)); //Place libre hier free = true
    	placeDao.persist(new Place(new Integer(38), strTomorrow)); //Place libre demain
    	placeDao.persist(new Place(new Integer(35), strTomorrow)); //Place libre demain
    	placeDao.persist(new Place(new Integer(36), MAIL_RESERVANT, strYearsteday)); //Place occupee hier
    	placeDao.persist(new Place(new Integer(37), strTomorrow)); //Place libre demain
	}
    
    @Test
    public void findAllFreeByDate_avec_date() {
    	List<Place> lesPlacesLibresAujourdhui = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", strDateToday), Restrictions.isNull("mailOccupant"));
    	Assert.assertEquals("On attend 2 places libres aujourd'hui", 2, lesPlacesLibresAujourdhui.size());
    }

//    @Test(expected=IllegalArgumentException.class)
//    public void findReleaseHistoryByPlace_sans_param() {
//    	placeDao.findReleaseHistoryByPlace(null);
//    }
    
    @Test
    public void findReleaseHistoryByPlace() {
    	List<Place> historiquePlace35 = placeDao.findPlacesByCriterions(Restrictions.eq("placeNumber", new Integer(35)));
    	Assert.assertEquals("On attend n places", 2, historiquePlace35.size());
    }
    
    
    @Test
    public void UserAPasDejaUnePlaceReserveDans10jours() {
    	List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", timePoint.plusDays(10).format(formatter)), Restrictions.eq("mailOccupant",MAIL_RESERVANT));
    	Assert.assertEquals( MAIL_RESERVANT+ " n'a pas réservé une place dans 4 jours", false, (listPlaceReserve!= null && listPlaceReserve.size()>0) ? true : false);
    }  
    
    @Test
    public void unePlaceEstLibreAujourdhui(){
    	List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", strDateToday), Restrictions.eq("placeNumber", new Integer(1)));
    	Assert.assertEquals( "La place 1 est libre aujourd'hui", true, (listPlaceReserve!= null && listPlaceReserve.size()>0) ? true : false);
    }
    
    
    @Test
    public void unePlaceNEstLibreAujourdhui(){
    	List<Place> listPlaceReserve = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", strDateToday), Restrictions.eq("placeNumber", new Integer(200)), Restrictions.isNull("mailOccupant"));
    	Assert.assertEquals( "La place 200 n'est libre aujourd'hui", false, (listPlaceReserve!= null && listPlaceReserve.size()>0) ? true : false);
    }
    
    
    @Test
    public void changementHeure(){
    	int nbPlace = 2;
    	int idPremierePlace = 3;
    	String dateReservation = strTomorrow;
    	if(LocalDateTime.now().getHour()<=Place.HEURE_CHANGEMENT_JOUR_RECHERCHE){
    		nbPlace = 3;
    		idPremierePlace = 2;
    		dateReservation = strDateToday;
    	}
    	List<Place> lesPlacesReserveesParDamien = placeDao.findPlacesByCriterions(Restrictions.eq("mailOccupant", MAIL_RESERVANT),Restrictions.ge("occupationDate", dateReservation));
    	Assert.assertEquals("On attend n places", nbPlace, lesPlacesReserveesParDamien.size());
    	Place premier = (Place) lesPlacesReserveesParDamien.get(0);
    	Assert.assertEquals("Damien a réservé la place "+premier.getPlaceNumber().intValue(), idPremierePlace , premier.getPlaceNumber().intValue());
    	Assert.assertEquals("Damien a réservé la place "+premier.getPlaceNumber().intValue()+" le " + dateReservation, dateReservation , premier.getOccupationDate());
    }

    @Ignore
    public void suppression() {
    	
    	placeDao.clearAllPlacesBeforeDate(LocalDate.now().plusDays(40));
    	
    	List<Place> lesPlacesLibresAujourdhui = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", strDateToday), Restrictions.isNull("mailOccupant"));
    	//List<Place> lesPlacesLibresAujourdhui = placeDao.findAllFreeByDate(timePoint);
    	Assert.assertEquals("On attend 0 places libres aujourd'hui", 0, lesPlacesLibresAujourdhui.size());
    	
    	lesPlacesLibresAujourdhui = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", strTomorrow), Restrictions.isNull("mailOccupant"));
    	Assert.assertEquals("On attend 0 places libres demain", 0, lesPlacesLibresAujourdhui.size());
    	
    	lesPlacesLibresAujourdhui = placeDao.findPlacesByCriterions(Restrictions.eq("occupationDate", strApresDemain), Restrictions.isNull("mailOccupant"));
    	Assert.assertEquals("On attend 0 places libres apres demain", 0, lesPlacesLibresAujourdhui.size());
    }
 
}
