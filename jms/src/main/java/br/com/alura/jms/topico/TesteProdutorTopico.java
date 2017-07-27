package br.com.alura.jms.topico;

import java.text.MessageFormat;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorTopico {
	
	public static void main(String args[]) throws Exception {

//		InitialContext context = new InitialContext();

		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");

		InitialContext context = new InitialContext(properties);
		
		// imports do package javax.jms
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");

		MessageProducer producer = session.createProducer(topico);

		String msgFmtStr = "<pedido><id>{0}</id></pedido>";
		MessageFormat msgFmt = new MessageFormat(msgFmtStr);
		
		for (int i = 1; i <= 2; i++) {
			
			Message message = session.createTextMessage(msgFmt.format(new Object[] {i}));
//			Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			producer.send(message);
		}
		
		session.close();
		connection.close();
		context.close();

	}

}
