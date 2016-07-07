package org.friends.app.view.route;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.friends.app.Messages;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Page de Login
 * 
 * @author michael lefevre
 */
@Component
public class LoginRoute implements TemplateViewRoute {

	private static final String KEY_EMAIL = "email";

	private static final String APPLICATION_PROPERTIES = "application.properties";
	static Properties properties;
	
	@Autowired
	private UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = Routes.getMap(request);
		User user = request.session().attribute("user");
		if (user != null) {
			Routes.redirect(user, response);
		} else if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onLogin(request, response, map);
		}

		return new ModelAndView(map, Templates.LOGIN);
	}

	protected void onLogin(Request request, Response response, Map<String, Object> map) {

		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		
		// En cas d'erreur
		map.put(KEY_EMAIL, email);


		User user = null;
		Properties tmp = new Properties();
		try {
			tmp.load(LoginRoute.class.getResourceAsStream(APPLICATION_PROPERTIES));
		} catch (IOException e) {
			System.out.println("erreur lecture application.properties");
		}
		properties = tmp;
		
		// Si administrateur
		if ((properties.getProperty("admin.email")).equals(email) &&
				(getEncryptedMD5Password(properties.getProperty("admin.password"))).equals(pwd)){
			Logger.getLogger("login").info("Admin logged in : " + email);
			map.put("admin", "true");
			user = new User(email, pwd);
			addAuthenticatedUser(request, user);
			Routes.redirect(user, response);
		}
		else{
			try {
				user = userService.authenticate(email, pwd);

				if (user != null) {
					Logger.getLogger("login").info("user logged in : " + user.getEmailAMDM());
					addAuthenticatedUser(request, user);
					Routes.redirect(user, response);
				} else {
					map.put(Routes.KEY_ERROR, "Utilisateur introuvable !");
				}

			} catch (Exception e) {
				map.put(Routes.KEY_ERROR, Messages.get(e.getMessage()));
			}

		}
		
	}

	private void addAuthenticatedUser(Request request, User user) {
		request.session().attribute("user", user);
	}
	
	private String getEncryptedMD5Password(String pass) {
		StringBuffer sb = new StringBuffer();
    	try{
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(pass.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	    } catch (NoSuchAlgorithmException ex) {

	    }
	     return sb.toString();
	}	
}
