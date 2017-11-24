package com.shakepoint.email.sender.manager;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.shakepoint.email.model.Email;
import com.shakepoint.email.sender.client.ClientProvider;
import com.shakepoint.email.sender.template.TemplateProvider;

public class EmailManager {

	@Inject
	private TemplateProvider templateProvider;
	
	@Inject
	private ClientProvider clientProvider;
	
	@Inject
	private Logger log;
	
	private static final Gson GSON = new Gson();
	
	public void send(final String emailAsJson){
		try {
			log.info("Trying to send email...");
			final Email email = GSON.fromJson(emailAsJson, Email.class);
			
			final String body = templateProvider.parseTemplate(email.getTemplateName(), email.getVariables());
			clientProvider.send(email.getTo(), email.getSubject(), body);
			log.info("Email sent");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			//TODO log somewhere that we need to check why is failing
		} 
	}
}
