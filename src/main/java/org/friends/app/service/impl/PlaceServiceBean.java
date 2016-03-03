package org.friends.app.service.impl;

import java.util.List;

import org.friends.app.dao.PlaceDao;

public class PlaceServiceBean {
	
	PlaceDao placedao = new PlaceDao();

	public List<Integer> getAvailable() {
		return placedao.findAllFree();
	}
}
