package com.shakepoint.email.sender.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Map;
import java.util.StringJoiner;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.shakepoint.email.sender.template.exception.TemplateNotFoundException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Singleton
public class FreemarkerTemplateProvider implements TemplateProvider {

	private Configuration cfg = null;
	private final String TEMPLATE_EXTENTION = "ftlh";
	
	@Inject
	private Logger log;

	@PostConstruct
	public void setup() {
		cfg = new Configuration(Configuration.VERSION_2_3_27);
		
		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
		try {
			cfg.setDirectoryForTemplateLoading(new File(createTemplateFolderPath()));
			//Check for template updates each 5min
			cfg.setTemplateUpdateDelayMilliseconds(60*5*1000);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		// Set the preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development*
		// TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// Don't log exceptions inside FreeMarker that it will thrown at you
		// anyway:
		cfg.setLogTemplateExceptions(false);

		// Wrap unchecked exceptions thrown during template processing into
		// TemplateException-s.
		cfg.setWrapUncheckedExceptions(true);
		
	}

	public String parseTemplate(String templateName, Map<String, Object> parameters) throws TemplateNotFoundException {
		try {
			final Template template = cfg.getTemplate(createTemplateName(templateName));
			final Writer stringWriter = new StringWriter();
			
			template.process(parameters, stringWriter);
			
			return stringWriter.toString();
		} catch (freemarker.template.TemplateNotFoundException e) {
			throw new TemplateNotFoundException(e);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private String createTemplateFolderPath(){
		return Paths.get(System.getProperty("jboss.server.config.dir"), "templates").toString();
	}
	
	private String createTemplateName(final String templateName){
		return new StringJoiner(".").add(templateName).add(TEMPLATE_EXTENTION).toString();
	}
}
