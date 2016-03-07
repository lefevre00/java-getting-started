package org.friends.app.view;

import org.friends.app.model.User;

import spark.Response;

public interface UserRoutes {
	
	public static void redirect(User user, Response response) {
		String dest = "/protected/search";
		if (user.getPlaceNumber() != null)
			dest = "/protected/sharePlace";
		response.redirect(dest);
	}
}
