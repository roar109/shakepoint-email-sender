package com.shakepoint.web.email.sender.client;

public interface ClientProvider {

	public void send(String to, String subject, String body);
}
