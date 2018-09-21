package com.shakepoint.web.email.sender.template;

import java.util.Map;

import com.shakepoint.web.email.sender.template.exception.TemplateNotFoundException;


public interface TemplateProvider {

	String parseTemplate(final String templateName, final Map<String, Object> parameters) throws TemplateNotFoundException;
}
