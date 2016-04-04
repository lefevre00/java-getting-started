package org.friends.app.service;

import org.friends.app.model.Place;

public class PlaceBuilder {

	public static PlaceBuilder unePlace() {
		return new PlaceBuilder();
	}

	public Place build() {
		return new Place();
	}
	
	public Place build(Integer number, String date) {
		return new Place(number, date);
	}
	
}
