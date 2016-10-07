/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
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
public class ValidTokenRoute implements TemplateViewRoute {

	@Autowired
	UserService service;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		Map<String, Object> map = Routes.getMap(request);
		map.put("title", "Validation de l'adresse email");

		String token = request.queryParams(Routes.PARAM_TOKEN_VALUE);
		String message = "Aucun code de validation fournit.";
		boolean success = false;

		if (!StringUtils.isEmpty(token)) {
			success = service.activate(token);
			message = success ? "Votre adresse email vient d'être validée, vous pouvez maintenant vous connecter."
					: "Echec lors de la validation de l'adresse email. Vérifiez le lien fournit et recommencez.";
		}

		map.put("message", message);
		if (success){
			map.put("ok", "ok");
			map.put("urlDest", "/user/login");
			map.put("libelleBtn", "Se connecter");
		}
		return new ModelAndView(map, Templates.MESSAGE_OK_KO);
	}

}
