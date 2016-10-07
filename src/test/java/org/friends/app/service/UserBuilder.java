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
package org.friends.app.service;

import java.lang.reflect.Field;

import org.friends.app.model.User;

import spark.utils.Assert;

public class UserBuilder {

	public static UserBuilder unUser() {
		return new UserBuilder();
	}

	private Integer id;
	private String email;
	private String mdp;
	private Integer place;

	public User build() {
		User back = new User(email, mdp);
		if (null != id) {
			Field field;
			try {
				field = User.class.getDeclaredField("id");
				field.setAccessible(true);
				field.set(back, id);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				org.junit.Assert.fail("Voir exception");
			}
		}
		if (null != place) {
			back.setPlaceNumber(place);
		}
		return back;
	}

	public UserBuilder id(int id) {
		this.id = id;
		return this;
	}

	public UserBuilder email(String email) {
		Assert.notNull(email);
		this.email = email;
		return this;
	}

	public UserBuilder mdp(String mot) {
		this.mdp = mot;
		return this;
	}

	public UserBuilder place(int place) {
		this.place = place;
		return this;
	}

}
