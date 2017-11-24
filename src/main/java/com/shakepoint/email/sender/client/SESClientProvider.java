package com.shakepoint.email.sender.client;

import javax.inject.Inject;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.github.roar109.syring.annotation.ApplicationProperty;
import com.github.roar109.syring.annotation.ApplicationProperty.Types;
import com.github.roar109.syring.annotation.ApplicationProperty.ValueType;

public class SESClientProvider implements ClientProvider{

	@Inject
	private AmazonSimpleEmailService awsService;
	
	@Inject
	@ApplicationProperty(name="aws.ses.default.from.email", type=Types.SYSTEM, valueType=ValueType.STRING)
	private String FROM;

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
		awsService.sendEmail(request);
	}
}
