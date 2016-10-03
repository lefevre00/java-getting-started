package org.friends.app.view.route;

import org.friends.app.ConfHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public abstract class AdminAuthRoute extends AuthenticatedRoute {

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		checkAuthenticated(request, response);
		checkAdmin(request, response);
		return doHandle(request, response);
	}

	private final void checkAdmin(Request request, Response response) {
		if (!isAdmin(request)) {
			response.redirect(ConfHelper.complementUrl() + Routes.ACCESS_DENIED);
		}
	}
}
