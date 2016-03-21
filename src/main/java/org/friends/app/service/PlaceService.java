package org.friends.app.service;

import java.util.List;

public interface PlaceService {

	String INVALID_NUMBER = "number.invalid";

	List<Integer> getShared();
	
	void releasePlace(Integer numerPlace);

}
