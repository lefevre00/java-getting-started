package org.friends.app.view;

import java.util.HashMap;
import java.util.List;
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
		
		T data = null;
		Map<String, Object> map = new HashMap<>();
		
		if (METHOD_POST.equalsIgnoreCase(request.requestMethod())) {
			data = bind(request);
			List<String> errors = validate(data);
			if (errors.isEmpty()) {
				submit(request, response, data);
			} else {
				map.put(ERROR, errors);
			}
		}
		
    	return new ModelAndView(map, getTemplate());
	}

	/**
	 * Build a model from the parameters received.
	 * @param request
	 * @return the model
	 */
	protected abstract T bind(Request request);
	
	/**
	 * Validate the model value depending on business rules.
	 * @param data
	 * @return
	 */
	protected abstract List<String> validate(T data);

	/**
	 * If the validation pass, the form would be submited through this callback. If not the form will be shown again to the user, with appropriate erros message to correct his mistakes.
	 * @param request
	 * @param response
	 * @param t
	 * @throws Exception
	 */
	protected abstract void submit(Request request, Response response, T t) throws Exception;

	/**
	 * Define on which template is the form is build uppon.
	 * @return
	 */
	protected abstract String getTemplate();
}
