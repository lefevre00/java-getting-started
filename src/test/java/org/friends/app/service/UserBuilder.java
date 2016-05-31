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
