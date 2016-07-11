package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

@Component
public class PasswordTokenRoute implements TemplateViewRoute {

	private static final String FIELD_PASSWORD = "pwd";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_TOKEN = "token";

	@Autowired
	private UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		String template = Templates.PASSWORD_NEW;

		Map<String, Object> map = Routes.getMap(request);
		String token = request.queryParams(FIELD_TOKEN);
		String email = request.queryParams(FIELD_EMAIL);
		String mdp = request.queryParams(FIELD_PASSWORD);

		if (StringUtils.isEmpty(token)) {
			map.put(Routes.KEY_ERROR, "Jeton invalide, contrôler le lien fourni dans l'email.");
			map.put(FIELD_EMAIL, email);
		} else {
			boolean result = userService.setPassword(email, token, mdp);
			if (result) {
				map.put("title", "Mot de passe modifié");
				map.put("message", "Votre mot de passe vient d'être modifié. Vous pouvez vous connecter.");
				map.put("ok", "ok");
				map.put("urlDest", "/user/login");
				map.put("libelleBtn", "Se connecter");
				template = Templates.MESSAGE_OK_KO;
			} else {
				map.put(FIELD_EMAIL, email);
				map.put(FIELD_TOKEN, token);
				map.put(Routes.KEY_ERROR, "Vérifier l'adresse email fournie.");
			}
		}
		return new ModelAndView(map, template);
	}
}
