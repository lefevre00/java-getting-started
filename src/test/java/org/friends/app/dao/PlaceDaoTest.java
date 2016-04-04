package org.friends.app.dao;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.friends.app.Configuration;
import org.friends.app.HibernateUtil;
import org.friends.app.model.Place;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlaceDaoTest {
	

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PlaceDao.DATE_PATTERN);
	static LocalDate timePoint = LocalDate.now();
	static String strDateToday  = timePoint.format(formatter);
	static String strTomorrow = timePoint.plusDays(1).format(formatter);
	static String strApresDemain = timePoint.plusDays(2).format(formatter);
	static String strYearsteday = timePoint.minusDays(1).format(formatter);
	
	
	private PlaceDao placeDao = new PlaceDao();
    
    private static String MAIL_RESERVANT = "damien.urvoix@amdm.fr";
    
    @BeforeClass
    public static void beforeClass() throws SQLException {
    	System.setProperty(Configuration.DEPLOY_MODE, "dev");
    	HibernateUtil.getSession();
    	
    }
    
    @Before
    public void createDatabase() throws SQLException {
    	placeDao = new PlaceDao();
    	
    }
    
    @After
    public void clearDataBase() throws SQLException {
    	placeDao.clearAllPlacesBeforeDate(timePoint);
    }
    
    @AfterClass
    public static void close(){
    	HibernateUtil.closeSession();
    }

	private void init() {
		placeDao.persist(new Place(new Integer(3), MAIL_RESERVANT, strTomorrow));
		placeDao.clearAllPlacesBeforeDate(timePoint.plusDays(40)); // suppresion de toutes les réservations
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
    public void testMethodesDAO() throws SQLException, URISyntaxException {
    	init();
    	List<Place> lesPlacesLibresAujourdhui = placeDao.findAllFreeByDate(LocalDate.now());
    	Assert.assertEquals("On attend 2 places libres aujourd'hui", 2, lesPlacesLibresAujourdhui.size());
    	List<Place> historiquePlace35 = placeDao.findReleaseHistoryByPlace(new Integer(35));
    	Assert.assertEquals("On attend n places", 2, historiquePlace35.size());
    	Assert.assertEquals( MAIL_RESERVANT+ " a réservé une place Aujourd'hui", true, placeDao.userAsDejaReserveUnePlaceAcetteDate(timePoint, MAIL_RESERVANT));
    	Assert.assertEquals( MAIL_RESERVANT+ " n'a pas réservé une place Aujourd'hui", false, placeDao.userAsDejaReserveUnePlaceAcetteDate(timePoint.plusDays(4), MAIL_RESERVANT));
    	Place isFreeToday = placeDao.findPlaceisFreeAtTheDate(new Integer(1), timePoint);

    	
    	Assert.assertNotNull(isFreeToday);
    	if(LocalDateTime.now().getHour()<=Place.HEURE_CHANGEMENT_JOUR_RECHERCHE){
    		// On est avant 20h, on affiche les places disponibles du jour,
	    	List<Place> lesPlacesReserveesParDamien = placeDao.findAllBookedPlaceByUser(MAIL_RESERVANT);
	    	Assert.assertEquals("On attend n places", 2, lesPlacesReserveesParDamien.size());
	    	Place premier = (Place) lesPlacesReserveesParDamien.get(0);
	    	Assert.assertEquals("Damien a réservé la place "+premier.getPlaceNumber().intValue(), 2 , premier.getPlaceNumber().intValue());
	    	Assert.assertEquals("Damien a réservé la place "+premier.getPlaceNumber().intValue()+" le " + strDateToday, strDateToday , premier.getOccupationDate());
	    	
	    	isFreeToday = placeDao.findPlaceisFreeAtTheDate(new Integer(2), timePoint);
	    	Assert.assertNull(isFreeToday);
	    	
    	}else{
    		// Après 20H, on affiche les places disponibles demain
    		List<Place> lesPlacesReserveesParDamien = placeDao.findAllBookedPlaceByUser(MAIL_RESERVANT);
        	Assert.assertEquals("On attend n places", 1, lesPlacesReserveesParDamien.size());
        	Place premier = (Place) lesPlacesReserveesParDamien.get(0);
	    	Assert.assertEquals("Damien a réservé la place "+premier.getPlaceNumber().intValue(), 35 , premier.getPlaceNumber().intValue());
	    	Assert.assertEquals("Damien a réservé la place "+premier.getPlaceNumber().intValue()+" le " + strApresDemain, strApresDemain , premier.getOccupationDate());

    	}
    	
    	placeDao.clearAllPlacesBeforeDate(timePoint.plusDays(40));
    	lesPlacesLibresAujourdhui = placeDao.findAllFreeByDate(timePoint);
    	Assert.assertEquals("On attend 0 places libres aujourd'hui", 0, lesPlacesLibresAujourdhui.size());
    	
    	lesPlacesLibresAujourdhui = placeDao.findAllFreeByDate(timePoint.plusDays(1));
    	Assert.assertEquals("On attend 0 places libres aujourd'hui", 0, lesPlacesLibresAujourdhui.size());
    	
    	lesPlacesLibresAujourdhui = placeDao.findAllFreeByDate(timePoint.minusDays(2));
    	Assert.assertEquals("On attend 0 places libres aujourd'hui", 0, lesPlacesLibresAujourdhui.size());
    }
 
}
