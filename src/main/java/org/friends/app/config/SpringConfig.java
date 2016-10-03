package org.friends.app.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.friends.app.ConfHelper;
import org.friends.app.DeployMode;
import org.friends.app.service.MailSender;
import org.friends.app.service.impl.ConsoleMailSender;
import org.friends.app.service.impl.SendGridMailSender;
import org.friends.app.service.impl.SmtpMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spark.utils.StringUtils;

@Configuration
public class SpringConfig {

	@Bean
	public MailSender getMailSender() {
		DeployMode mode = ConfHelper.getDeployMode();
		switch (mode) {
		case HEROKU:
			return new SendGridMailSender();
		case STANDALONE:
			if (sendMailDefined()) {
				return new SmtpMailSender();
			}
		default:
			return new ConsoleMailSender();
		}
	}

	private boolean sendMailDefined() {
		try {
			Context context = new InitialContext();
			if (context == null)
				return false;
			String sendMail = (String) context.lookup("java:comp/env/sendMail");
			if (StringUtils.isEmpty(sendMail)) {
				return false;
			} else {
				return true;
			}
		} catch (NamingException e) {
			return false;
		}
	}
}
