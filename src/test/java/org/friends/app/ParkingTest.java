package org.friends.app;

import org.junit.BeforeClass;

public class ParkingTest {

	@BeforeClass
	public static void setup() {
    	System.setProperty(Configuration.DEPLOY_MODE, "dev");
	}
}
