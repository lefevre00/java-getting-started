package org.friends.app.view;

import org.friends.app.ConfHelper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

	public void start() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ConfHelper.class);
		ctx.refresh();
	}
}
