package org.friends.app;

import org.junit.BeforeClass;
import org.junit.Ignore;

@Ignore
public class ClientTest {

	@BeforeClass
	public static void setup() {
		System.setProperty(DeployMode.PROPERTY, DeployMode.TEST.getCode());
	}

}
