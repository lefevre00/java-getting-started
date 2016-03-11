package org.friends.app.view;

import static org.friends.app.Configuration.getPort;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import org.friends.app.Constants;
import org.friends.app.model.Session;
import org.friends.app.model.User;
import org.friends.app.service.impl.UserServiceBean;

import com.heroku.sdk.jdbc.DatabaseUrl;

import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;
import spark.utils.StringUtils;

public class Application {

	private static Application instance;
	UserServiceBean userService = new UserServiceBean();

	public Application() {
		if (instance == null) instance = this;
	}

	public void start() {
		port(getPort());
		staticFileLocation("/public");

		/*
		 * Auto compress response 
		 */
		after((request, response) -> {
			String header = request.raw().getHeader("Accept-Encoding");
			if (header != null && header.contains("gzip"))
				response.header("Content-Encoding", "gzip");
		});
		
		/*
		 * Controle that user first logged in
		 */
		Filter checkLoggedIn = new Filter() {
			@Override
			public void handle(Request request, Response response) throws Exception {
				boolean userFound = false;

				// 1 : try to find user in session
				User authenticatedUser = getAuthenticatedUser(request);
				if (authenticatedUser != null) {
					userFound = true;
				} else {
					
					// 2. Try to find user using cookie
					String cookie = request.cookie(Constants.COOKIE);
					if (!StringUtils.isEmpty(cookie)) {
						
						User user = userService.findUserByCookie(cookie);
						if (user != null) {
							request.session().attribute("user", user);
							userFound = true;
						} else {
							// Clean cookie if no user
							response.removeCookie(Constants.COOKIE);
						}
					}
				}
				
				if (!userFound) {
					response.redirect(Routes.LOGIN);
				}
			}
		};
		before("/protected/*", checkLoggedIn); 
		before("/", checkLoggedIn);

		get("/", (request, response) -> {
			return new ModelAndView(null, "index.ftl");
		}, new FreeMarkerEngine());

		/*
		 * User login 
		 */
		LoginRoute loginRoute = new LoginRoute();
		get(Routes.LOGIN, loginRoute, new FreeMarkerEngine());
		post(Routes.LOGIN, loginRoute, new FreeMarkerEngine());

		/*
		 * Déconnexion
		 */
		get(Routes.LOGOUT, (req, res) -> {
			removeAuthenticatedUser(req);
			res.removeCookie(Constants.COOKIE);
			return null;
		});

		/* 
		 * User register 
		 */
		RegisterRoute registerRoute = new RegisterRoute();
		get(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		post(Routes.REGISTER, registerRoute, new FreeMarkerEngine());

		/*
		 * Forgot password
		 */
		ForgottenPwdRoute forgottenPwdRoute = new ForgottenPwdRoute();
		get(Routes.PASSWORD_LOST, forgottenPwdRoute, new FreeMarkerEngine());
		post(Routes.PASSWORD_LOST, forgottenPwdRoute, new FreeMarkerEngine());


		/*
		 * Places booking 
		 */
		get(Routes.PLACE_SEARCH, new SearchRoute(), new FreeMarkerEngine());
		//post("/protected/search", new SearchRoute(), new FreeMarkerEngine());

		get("/protected/book/:placeId", (req, res) -> {
			return "Are you looking for " + req.params(":placeId");
		});

		/*
		 * Share a place
		 */
		SharePlace shareRoute = new SharePlace();
		get(Routes.PLACE_SHARE, shareRoute, new FreeMarkerEngine()); 
		post(Routes.PLACE_SHARE, shareRoute, new FreeMarkerEngine());//(req, res) -> "Vous libérez la place   " + req.queryParams("number") +" du " + req.queryParams("dateDebut") +" du " + req.queryParams("dateFin"));

		/*
		 * Set cookie if needed
		 */
		Filter setCookieFilter = (request, response) -> {
			User authUser = getAuthenticatedUser(request); 
			if (authUser != null) {
				String cookie = request.cookie(Constants.COOKIE);
				if (cookie == null) {
					Session session = userService.createSession(authUser);
					response.cookie("/", Constants.COOKIE, session.getCookie(), Constants.COOKIE_DURATION, false);
				}
			}
		};
		after(Routes.PLACE_SEARCH, setCookieFilter);
		after(Routes.PLACE_SHARE, setCookieFilter);
		after("/", setCookieFilter);
		
		/* Doc */
		// TODO someone at some point ;)
//		get("/help", (req, res) -> "Nothing yet at help");

		
/*		
	    get("/db", (req, res) -> {
	      Connection connection = null;
	      Map<String, Object> attributes = new HashMap<>();
	      try {
	        connection = getConnection();

	        Statement stmt = connection.createStatement();
	        // Création de la structure
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (user_id INT NOT NULL, email_amdm VARCHAR(255) NOT NULL, place_id INT, pwd varchar(255) NOT NULL, PRIMARY KEY (user_id, email_amdm));");
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS places (place_id int NOT NULL, mail_occupant varchar(255), occupation_date varchar(10) NOT NULL, PRIMARY KEY (place_id, occupation_date));");
	        // Peuplement des users
	        stmt.executeUpdate("DELETE FROM users");
	        stmt.executeUpdate("INSERT INTO USERS (USER_ID, email_amdm, place_id, pwd) SELECT 1, 'william.verdeil@amdm.fr', 141 , 'wv' WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 1 AND email_amdm = 'william.verdeil@amdm.fr');");
*	   
	 * stmt.executeUpdate("INSERT INTO users (user_id, email_amdm, place_id, pwd)  "+
	    	        "SELECT 1, 'william.verdeil@amdm.fr', 141 , 'wv') FROM users "+
	    	         "WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 1 AND email_amdm = 'william.verdeil@amdm.fr')");    
	        stmt.executeUpdate("INSERT INTO users (user_id, email_amdm, place_id, pwd) "+
	    	        "SELECT 2, 'abdel.tamditi@amdm.fr', 133 , 'at') FROM places "+
	    	         "WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 2 AND email_amdm = 'abdel.tamditi@amdm.fr');");
	        
	        stmt.executeUpdate("INSERT INTO users (user_id, email_amdm, place_id, pwd) "+
	    	        "SELECT 3, 'michael.lefevre@amdm.fr', 87 , 'ml') FROM places "+
	    	         "WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 3 AND email_amdm = 'michael.lefevre@amdm.fr');");
	        stmt.executeUpdate("INSERT INTO users (user_id, email_amdm, place_id, pwd) "+
	    	        "SELECT 4, 'damien.urvoix@amdm.fr', null , 'ml') FROM places "+
	    	         "WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 4 AND email_amdm = 'damien.urvoix@amdm.fr');");
	        
	        stmt.executeUpdate("INSERT INTO users (user_id, email_amdm, place_id, pwd) "+
	    	        "SELECT 5, 'jean-pierre.cluzel@amdm.fr', null , 'jpc') FROM places "+
	    	         "WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 5 AND email_amdm = 'jean-pierre.cluzel@amdm.fr');");
	      


	        ResultSet rs = stmt.executeQuery("SELECT email_amdm FROM users");

	        ArrayList<String> output = new ArrayList<String>();
	        while (rs.next()) {
	          output.add( "Read from DB: " + rs.getTimestamp("email_amdm"));
	        }

	        attributes.put("results", output);
	        return new ModelAndView(attributes, "db.ftl");
	      } catch (Exception e) {
	        attributes.put("message", "There was an error: " + e);
	        return new ModelAndView(attributes, "error.ftl");
	      } finally {
	        if (connection != null) try{connection.close();} catch(SQLException e){}
	      }
	    }, new FreeMarkerEngine());
		
	*/	

		/* Intercept 404 */
		get("*", (request, response) -> {
			response.redirect(Routes.LOGIN);
			return "404";
		});
	}

	protected Connection getConnection() throws SQLException, URISyntaxException {
		return DatabaseUrl.extract().getConnection();
	}

	public static Application instance() {
		return instance;
	}
	
	private User getAuthenticatedUser(Request request) {
		return request.session().attribute("user");
	}

	private void removeAuthenticatedUser(Request request) {
		request.session().removeAttribute("user");
	}
}
