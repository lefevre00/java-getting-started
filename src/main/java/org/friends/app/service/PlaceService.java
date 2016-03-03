package org.friends.app.service;

import java.util.List;

public interface PlaceService {

	List<Integer> getShared();
	
	void releasePlace(Integer numerPlace);

}
