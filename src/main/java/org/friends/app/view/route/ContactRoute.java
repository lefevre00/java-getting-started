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
		Map<String, String> params = request.params();
		Map<String, Object> map = new HashMap<>();
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			String nom = params.get("nom");
			String mail = params.get("email");
			String message = params.get("message");
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
