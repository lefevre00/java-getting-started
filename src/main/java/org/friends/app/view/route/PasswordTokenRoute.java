package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.view.Templates;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

public class PasswordTokenRoute implements TemplateViewRoute {

	private static final String FIELD_PASSWORD = "pwd";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_TOKEN = "token";
	UserServiceBean userService = new UserServiceBean();
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		String template = "pwd_new.ftl";

		Map<String, Object> map = Routes.getMap(request);
		String token = request.queryParams(FIELD_TOKEN);
		String email = request.queryParams(FIELD_EMAIL);
		String mdp = request.queryParams(FIELD_PASSWORD);
		
		if (StringUtils.isEmpty(token)) {
			map.put("error", "Jeton invalide, contrôler le lien fourni dans l'email.");
			map.put(FIELD_EMAIL, email);
		} else {
			boolean result = userService.setPassword(email, token, mdp);
			if (result) {
				map.put("title", "Mot de passe modifié");
				map.put("message", "Votre mot de passe vient d'être modifié. Vous pouvez vous connecter.");
				map.put("ok", "ok");
				template = Templates.MESSAGE_OK_KO;
			} else {
				map.put(FIELD_EMAIL, email);
				map.put(FIELD_TOKEN, token);
				map.put("error", "Vérifier l'adresse email fournie.");
			}
		}
		return new ModelAndView(map, template);
	}
}
