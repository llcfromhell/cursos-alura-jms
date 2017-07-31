package br.com.alura.jms.log;


import java.util.Properties;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ProdutorMensagemLog {

	public static void main(String args[]) throws Exception {


		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.log", "fila.log");

		InitialContext context = new InitialContext(properties);
		
		// imports do package javax.jms
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("log");

		MessageProducer producer = session.createProducer(fila);
//		Message message = session.createTextMessage("INFO | .....");
//		producer.send(message,DeliveryMode.NON_PERSISTENT, 2, 50000);
		
//		Message message2 = session.createTextMessage("ERROR | .....");
//		producer.send(message2,DeliveryMode.NON_PERSISTENT, 9, 50000);
//		
//		Message message3 = session.createTextMessage("WARNING | .....");
//		producer.send(message3,DeliveryMode.NON_PERSISTENT, 4, 50000);
//		
//		Message message4 = session.createTextMessage("DEBUG | .....");
//		producer.send(message4,DeliveryMode.NON_PERSISTENT, 0, 50000);
		
		

		session.close();
		connection.close();
		context.close();

	}

}
