package org.friends.app.view;

import org.friends.app.DeployMode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

	protected ApplicationContext applicationContext;

	public void start(DeployMode mode) {
		System.setProperty(DeployMode.PROPERTY, mode.getCode());
		applicationContext = new ClassPathXmlApplicationContext("/context/" + mode.name().toLowerCase() + ".xml");
	}
}
