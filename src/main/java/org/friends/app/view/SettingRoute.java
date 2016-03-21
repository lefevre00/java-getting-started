package org.friends.app.view;

import org.friends.app.view.route.AuthenticatedRoute;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SettingRoute extends AuthenticatedRoute {

	@Override
	protected ModelAndView doHandle(Request request, Response response) {
		return new ModelAndView(null, Templates.SETTING);
	}
}
