package org.friends.app;

public enum DeployMode {

	TEST("TEST"), HEROKU("HEROKU"), STANDALONE("STANDALONE");

	public final static String PROPERTY = "DEPLOY_MODE";

	private String code;

	private DeployMode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
