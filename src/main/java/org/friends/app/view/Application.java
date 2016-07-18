package org.friends.app.view;

import org.friends.app.ConfHelper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

	public void start() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ConfHelper.class);
		ctx.refresh();

		for (String name : ctx.getBeanDefinitionNames()) {
			System.out.println("bean define : " + name);
		}
		RoutesLoader routesLoader = ctx.getBean(RoutesLoader.class);
		routesLoader.init(ctx);
	}
}
