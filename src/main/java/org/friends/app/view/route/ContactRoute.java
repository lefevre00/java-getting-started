package org.friends.app.view.route;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.service.impl.MailServiceBean;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

public class ContactRoute  implements TemplateViewRoute {
	
	private MailServiceBean mailService = new MailServiceBean();

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			String mail = request.queryParams("mail");
			String nom = request.queryParams("nom");
			String message = request.queryParams("message");
			if (StringUtils.isEmpty(nom) ||
					StringUtils.isEmpty(mail) ||
					StringUtils.isEmpty(message) ) throw new RuntimeException("Erreur saisie formulaire contact");
				sendContact(nom, mail, message);
		}
		return new ModelAndView(map, "contactSend.ftl");
	}
	
	
	private void sendContact(String nom, String mail, String message) {
		mailService.sendContact(nom, mail, message);
	}

}
