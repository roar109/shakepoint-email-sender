package com.shakepoint.web.email.sender.mdb;

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

import com.shakepoint.integration.jms.client.utils.JmsUtils;

@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@MessageDriven(name = "DummyMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "dummyq"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class DummyMDB implements MessageListener {
	
	@Inject
	private Logger log;

	public void onMessage(Message message) {
		log.info("--------------------");
		log.info(JmsUtils.getText(message));
		// After getting the message we need, trigger a CDI event to handle it async
		log.info("--------------------");
	}

}
