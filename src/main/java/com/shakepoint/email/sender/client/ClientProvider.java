package com.shakepoint.email.sender.client;

public interface ClientProvider {

	public void send(String to, String subject, String body);
}
