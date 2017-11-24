package com.shakepoint.email.sender.manager;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.shakepoint.email.model.Email;
import com.shakepoint.email.sender.template.TemplateProvider;

public class EmailManager {

	@Inject
	private TemplateProvider templateProvider;
	
	@Inject
	private Logger log;
	
	private static final Gson GSON = new Gson();
	
	public void send(final String emailAsJson){
		final Email email = GSON.fromJson(emailAsJson, Email.class);
		
		try {
			final String body = templateProvider.parseTemplate(email.getTemplateName(), email.getVariables());
			log.info(body);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
	}
}
