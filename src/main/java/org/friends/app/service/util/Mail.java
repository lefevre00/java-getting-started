package org.friends.app.service.util;

public class Mail {

	private String subject;
	private String body;
	private String replyTo;
	private String from;
	private String[] dest;

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String[] tos) {
		this.dest = tos;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String getFrom() {
		return from;
	}

	public String[] getDest() {
		return dest;
	}
}
