package com.shakepoint.email.email.sender.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.shakepoint.integration.jms.client.handler.JmsHandler;

@Path("dummy")
public class DummyResource {

	@Inject
	private JmsHandler jmsHandler;

	@GET
	@Path("queue")
	public Response triggerDummyQueue() {
		jmsHandler.send("dummyq","Hello from the other side " + System.currentTimeMillis());
		return Response.ok("ok").build();
	}

}
