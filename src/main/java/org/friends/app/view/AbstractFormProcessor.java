package org.friends.app.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.Request;
import spark.Response;

public abstract class AbstractFormProcessor<T> {
	
	private List<String> errors = new ArrayList<>();

	public void handle(Request request, Response response) {
		T t = validate(request);
		if (errors .isEmpty()) {
			submit(request, response, t);
		}
	}

	public Map<String, Object> toMap() {
		// TODO
		return null;
	}
	
	/**
	 * Validate the model value depending on business rules.
	 * @param data
	 * @return
	 */
	protected abstract T validate(Request request);

	/**
	 * If the validation pass, the form would be submited through this callback. If not the form will be shown again to the user, with appropriate erros message to correct his mistakes.
	 * @param request
	 * @param response
	 * @param t
	 * @throws Exception
	 */
	protected abstract void submit(Request request, Response response, T model);

}
