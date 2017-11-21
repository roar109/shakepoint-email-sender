package com.shakepoint.email.email.sender.jms;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

@ApplicationScoped
public class JmsHandler {

	@Resource(lookup = "java:jboss/exported/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Inject
	private Logger log;

	public JmsHandler() {
	}

	/**
	 * For use on CDI produces method
	 * */
	public JmsHandler(final ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void send(final String queueName, final String message) {
		try {
			Connection conn = null;
			try {
				conn = connectionFactory.createConnection();
				conn.start();

				Session session = null;
				try {
					session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Destination destination = session.createQueue(queueName);

					MessageProducer oMessageProducer = session.createProducer(destination);
					TextMessage textMessage = session.createTextMessage();
					textMessage.setText("Hello from the other side " + System.currentTimeMillis());
					oMessageProducer.send(textMessage);
				} finally {
					if (session != null) {
						session.close();
					}
				}
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
