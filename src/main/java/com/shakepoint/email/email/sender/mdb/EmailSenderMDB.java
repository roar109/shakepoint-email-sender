package com.shakepoint.email.email.sender.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@MessageDriven(name = "EmailSenderMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "shakepoint.integration.email.send"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class EmailSenderMDB  implements MessageListener{
	
	@Inject
	private Logger log;

	public void onMessage(Message message) {
		log.info(message);
	}

}
