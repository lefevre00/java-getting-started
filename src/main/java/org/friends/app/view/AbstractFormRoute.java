package org.friends.app.view;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public abstract class AbstractFormRoute<T> implements TemplateViewRoute {

	private static final String METHOD_POST = "POST";
	protected static final String ERROR = "error";
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		if (METHOD_POST.equalsIgnoreCase(request.requestMethod())) {
			AbstractFormProcessor<T> processor = getProcessor();
			processor.handle(request, response);
			map = processor.toMap();
		}
		
    	return new ModelAndView(map, getTemplate());
	}

	protected abstract AbstractFormProcessor<T> getProcessor();

	/**
	 * Define on which template is the form is build uppon.
	 * @return
	 */
	protected abstract String getTemplate();
}
