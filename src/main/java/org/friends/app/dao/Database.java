package org.friends.app.dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import org.friends.app.view.Application;

public class Database {

	static Application app = Application.instance();
	
	public static Connection getConnection() throws SQLException, URISyntaxException {	
		return app.getConnection() ;
	}
	
	public static String sqlPLACEFindFreeByDate = "SELECT id FROM places WHERE email_occupant IS NULL and occupation_date = ?";
	
	public static String sqlPLACETakeByDate = "UPDATE email_occupant FROM places WHERE id = ? and occupation_date = ?";
	
	/*Permet de libérer un place */
	public static String sqlPLACEShare = "INSERT INTO places (id, email_occupant, occupation_date)"+
"SELECT ?, null, DATE(?) FROM places"+
 "WHERE NOT EXISTS (SELECT 1 FROM places"+ 
                    "WHERE id = ?"+
                     " AND occupation_date = DATE(?));";
	
	
	public static String sqlUSERFindByEmail = "SELECT * FROM Users WHERE email=':email'";

	public static String sqlUSERCreate = "INSERT INTO Users (id, email, place_id, password, tokenMail, tokenPassword) values ('next_value', ? , ?, ? ,?, null)";
	

}
