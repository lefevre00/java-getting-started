package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.service.MailService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

@Component
public class ContactRoute implements TemplateViewRoute {

	@Autowired
	private MailService mailService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		Map<String, Object> map = Routes.getMap(request);

		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			String mail = request.queryParams("mail");
			String nom = request.queryParams("nom");
			String message = request.queryParams("message");
			if (StringUtils.isEmpty(nom) || StringUtils.isEmpty(mail) || StringUtils.isEmpty(message))
				throw new RuntimeException("Erreur saisie formulaire contact");
			mailService.sendContact(nom, mail, message);
		}
		return new ModelAndView(map, Templates.SEND_CONTACT);
	}
}
