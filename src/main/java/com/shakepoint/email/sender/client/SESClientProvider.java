package com.shakepoint.email.sender.client;

import javax.inject.Inject;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.github.roar109.syring.annotation.ApplicationProperty;
import com.github.roar109.syring.annotation.ApplicationProperty.Types;
import com.github.roar109.syring.annotation.ApplicationProperty.ValueType;
import org.apache.log4j.Logger;

public class SESClientProvider implements ClientProvider {

    @Inject
    private AmazonSimpleEmailService awsService;

    @Inject
    @ApplicationProperty(name = "aws.ses.default.from.email", type = Types.SYSTEM, valueType = ValueType.STRING)
    private String FROM;

    @Inject
    private Logger log;

    public void send(final String to, final String subject, final String body) {
        final SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8")
                                        .withData(body)
                                )
                        )
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(subject)
                        )
                )
                .withSource(FROM)
                .withReplyToAddresses(FROM);
        SendEmailResult result = awsService.sendEmail(request);
        System.out.println("-------------- Email details -----------------");
        System.out.println((String.format("Email ID: %s", result.getMessageId()));
        ResponseMetadata responseMetadata = result.getSdkResponseMetadata();
        System.out.println((String.format("%s", responseMetadata.getRequestId()));
    }
}
