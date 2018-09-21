package com.shakepoint.web.email.sender.manager;

import javax.inject.Inject;

import com.shakepoint.web.email.sender.template.TemplateProvider;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.shakepoint.email.model.Email;
import com.shakepoint.web.email.sender.client.ClientProvider;

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
			final Email email = GSON.fromJson(emailAsJson, Email.class);
			
			final String body = templateProvider.parseTemplate(email.getTemplateName(), email.getVariables());
			clientProvider.send(email.getTo(), email.getSubject(), body);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			//TODO log somewhere that we need to check why is failing
		} 
	}
}
