package org.friends.app.service.impl;

import org.friends.app.service.MailException;
import org.friends.app.service.MailSender;
import org.friends.app.service.util.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpMailSender implements MailSender {

	private JavaMailSender javaMailSender;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void send(Mail mail) throws MailException {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mail.getDest());
		msg.setFrom(mail.getFrom());
		msg.setReplyTo(mail.getReplyTo());
		msg.setSubject(mail.getSubject());
		msg.setText(mail.getBody());

		try {
			javaMailSender.send(msg);
		} catch (org.springframework.mail.MailException ex) {
			throw new MailException("Unable to send mail via Smtp", ex);
		}
	}

}
