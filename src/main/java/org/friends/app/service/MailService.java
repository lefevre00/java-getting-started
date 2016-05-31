package org.friends.app.service;

import org.friends.app.model.User;

public interface MailService {

	void sendWelcome(User user, String applicationUrl);

	void sendLostPassword(User user, String applicationUrl);

	void sendContact(String nom, String mail, String message);

}