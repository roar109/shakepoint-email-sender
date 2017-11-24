package com.shakepoint.email.sender.resource;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.shakepoint.email.model.Email;
import com.shakepoint.integration.jms.client.handler.JmsHandler;

@Path("dummy")
public class DummyResource {

	@Inject
	private JmsHandler jmsHandler;

	@GET
	@Path("queue")
	public Response triggerDummyQueue() {
		Email e = new Email();
		e.setSubject("Randome subjct");
		e.setTemplateName("example1");
		e.setTo("roar109@gmail.com");
		Map<String, Object> strings =  new HashMap<String, Object>();
		strings.put("name", "vale nombre");
		e.setVariables(strings);
		
		jmsHandler.send("shakepoint.integration.email.send",e.toJson());
		return Response.ok("ok").build();
	}

}
