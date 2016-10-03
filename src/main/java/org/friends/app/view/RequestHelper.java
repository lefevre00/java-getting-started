package org.friends.app.view;

import org.friends.app.ConfHelper;
import org.friends.app.DeployMode;

import spark.Request;
import spark.utils.Assert;

public final class RequestHelper {

	public static String getAppUrl(Request req) {
		Assert.notNull(req);
		String url = req.url();
		String back = url.substring(0, url.length() - req.uri().length());
		if(DeployMode.STANDALONE.equals(ConfHelper.getDeployMode())){
			back = "https://"+ req.host();
		}
		return back;
	}

}
