package org.friends.app.view;

import org.friends.app.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

	RoutesLoader routesLoader;

	public void start() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(Configuration.class);
		ctx.refresh();

		
		for (String name : ctx.getBeanDefinitionNames()) {
			System.out.println("bean define : " + name);
		}
		routesLoader = ctx.getBean(RoutesLoader.class);
		routesLoader.init(ctx);
	}
}
