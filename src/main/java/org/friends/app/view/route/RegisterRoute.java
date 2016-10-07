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
import org.friends.app.validator.EmailValidator;
import org.friends.app.view.RequestHelper;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

@Component
public class RegisterRoute implements TemplateViewRoute {

	private static final String EMAIL = "email";

	@Autowired
	UserService userService;

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {

		Map<String, Object> map = Routes.getMap(request);
		if ("POST".equalsIgnoreCase(request.requestMethod())) {
			onRegister(request, response, map);
		}else {
			if(!ConfHelper.INSCRIPTION_LIBRE) {
				String tokenMail = request.queryParams(Routes.PARAM_TOKEN_VALUE);
				String email = request.queryParams(Routes.PARAM_EMAIL_VALUE);
				String placeNumber = request.queryParams(Routes.PARAM_PLACE_NUMBER_VALUE);
				if(tokenMail != null && email != null) {
					map.put(Routes.PARAM_TOKEN_VALUE, tokenMail);
					map.put(Routes.PARAM_PLACE_NUMBER_VALUE, placeNumber);
					map.put(EMAIL, email);
				} 
			}
		}

		return new ModelAndView(map, Templates.REGISTER);
	}

	protected void onRegister(Request request, Response response, Map<String, Object> map) {

		String email = request.queryParams("email");
		String pwd = request.queryParams("pwd");
		String placeNumber = request.queryParams("placeNumber");
		String tokenMail = request.queryParams(Routes.PARAM_TOKEN_VALUE);
		
		try {
			if (Strings.isNullOrEmpty(email))
				throw new Exception(UserService.EMAIL_REQUIRED);

			if (!EmailValidator.isValid(email))
				throw new Exception(UserService.EMAIL_ERROR);

			if (Strings.isNullOrEmpty(pwd))
				throw new Exception(UserService.PWD_REQUIRED);
			
			if(!Strings.isNullOrEmpty(tokenMail) 
					&& !userService.findUserByEmailAndToken(email,tokenMail)) 
						throw new Exception(UserService.VALIDATION_TOKEN_ERROR);

			User user = new User(email, pwd, StringUtils.isEmpty(placeNumber) ? null : Integer.parseInt(placeNumber));

			User userExiste = userService.findUserByEmail(user.getEmailAMDM());
			if (userExiste == null) {
				if(ConfHelper.INSCRIPTION_LIBRE){
					user = userService.create(user, RequestHelper.getAppUrl(request));
					response.redirect(ConfHelper.complementUrl() + Routes.REGISTRED);	
				} else {
					map.put(Routes.KEY_ERROR, "Vous n'êtes pas autorisé à utiliser cette application");
				}
				
			} else {
				if(ConfHelper.INSCRIPTION_LIBRE){
					map.put(Routes.KEY_ERROR, "Un compte existe déjà avec cette adresse email !");
				} else {
					// L'utilisateur vient de confirmer son mail et de créeer son mot de passe
					userService.updateInscriptionUser(userExiste, pwd, RequestHelper.getAppUrl(request));
					response.redirect(ConfHelper.complementUrl() + Routes.LOGIN + "?activation=ok");	
				}
			}
			map.put(EMAIL, email);

		} catch (NumberFormatException e) {
			map.put(Routes.KEY_ERROR, "Le numéro de place est un numéro !");
		} catch (Exception e) {

			if (UserService.EMAIL_REQUIRED.equals(e.getMessage()))
				map.put(Routes.KEY_ERROR, "Vous devez saisir une adresse email !");

			if (UserService.EMAIL_ERROR.equals(e.getMessage()))
				map.put(Routes.KEY_ERROR, "L'email saisi est invalide !");

			if (UserService.PWD_REQUIRED.equals(e.getMessage()))
				map.put(Routes.KEY_ERROR, "Vous devez saisir un mot de passe !");

			map.put(EMAIL, email);
		}
	}
}
