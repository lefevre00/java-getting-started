package org.friends.app.view;

import java.security.AccessControlException;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public abstract class AuthenticatedRoute implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		checkAuthenticated(request);
		return doHandle(request, response);
	}

	protected abstract ModelAndView doHandle(Request request, Response response);

	private void checkAuthenticated(Request req) {
		if (req.session().attribute("user") == null)
			throw new AccessControlException("This page can only be accessed by authenticated user.");
	}

}
