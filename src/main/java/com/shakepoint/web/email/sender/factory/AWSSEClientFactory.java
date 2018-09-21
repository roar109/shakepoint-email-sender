package com.shakepoint.web.email.sender.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

public class AWSSEClientFactory {

	@Produces
	@ApplicationScoped
	public AmazonSimpleEmailService createSEClientService() {
		return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	}
}
