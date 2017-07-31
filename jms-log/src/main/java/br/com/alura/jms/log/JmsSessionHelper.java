package br.com.alura.jms.log;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsSessionHelper {
	
	InitialContext context;
	Connection connection;
	Session session;
	
	private void start() throws Exception {
		
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.log", "fila.log");

		this.context = new InitialContext(properties);
		
		this.connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
		connection.start();
		
		this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
	}
	
	public MessageProducer createProducer(String destination) throws Exception {
		start();
		return this.session.createProducer((Destination) this.context.lookup(destination));
	}

	public MessageConsumer createConsumer(String destination) throws Exception {
		start();
		return this.session.createConsumer((Destination) this.context.lookup(destination));
	}
	
	public void quit() throws Exception {
		this.session.close();
		this.connection.close();
		this.connection.close();
	}
	
}
