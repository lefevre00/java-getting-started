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

import org.friends.app.ConfHelper;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

@Component
public class ChangePwdRoute implements TemplateViewRoute {

	@Autowired
	UserService userService;
	
	private String template;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		template = Templates.PASSWORD_CHANGE;
		
		Map<String, Object> map = Routes.getMap(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			
			User user = request.session().attribute("user");
			
			String pwd = request.queryParams("pwd");
			String newPwd = request.queryParams("newPwd");
			String confirmPwd = request.queryParams("confirmPwd");

			System.out.println("\n\n");
			System.out.println("pwd : " + pwd);
			System.out.println("new pwd : " + newPwd);
			System.out.println("new pwd2 : " + confirmPwd);
			System.out.println("\n\n");
			

			if (user == null) {
				map.put(Routes.KEY_ERROR, "Utilisateur introuvable !");	
			}
			else{
				if ( !pwd.equals(user.getPwd()) ){
					map.put(Routes.KEY_ERROR, "Le mot de passe saisi est incorrect !");	
				}
				else if ( !newPwd.equals(confirmPwd) ){
					map.put(Routes.KEY_ERROR, "Les nouveaux mots de passe saisis ne sont pas identiques !");
				}
				else{
					
					boolean result = userService.changePassword(user.getEmailAMDM(), newPwd);
					if (result) {
						map.put("title", "Modification du mot de passe");
						map.put("message", "Votre mot de passe vient d'être modifié avec succès.");
						map.put("ok", "ok");
						map.put("urlDest", ConfHelper.complementUrl() + "/protected/setting");
						map.put("libelleBtn", "Retour");
						template = Templates.MESSAGE_OK_KO;
					} else {
						map.put(Routes.KEY_ERROR, "Un problème est survenu lors de la modification du mot de passe !");
					}				
					
				}
				
			}			
			
		}

		return new ModelAndView(map, template);
	}


}
