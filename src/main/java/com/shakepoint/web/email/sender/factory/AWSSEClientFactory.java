package com.shakepoint.web.email.sender.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

public class AWSSEClientFactory {

    @Produces
    @ApplicationScoped
    public AmazonSimpleEmailService createSEClientService() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setClientExecutionTimeout(100000);
        clientConfiguration.setConnectionTimeout(100000);
        clientConfiguration.setMaxConnections(10);
        clientConfiguration.setMaxConsecutiveRetriesBeforeThrottling(20);
        clientConfiguration.setMaxErrorRetry(100);
        clientConfiguration.setConnectionMaxIdleMillis(100000);
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withClientConfiguration(clientConfiguration)
                .withRegion(Regions.US_EAST_1).build();
    }
}
