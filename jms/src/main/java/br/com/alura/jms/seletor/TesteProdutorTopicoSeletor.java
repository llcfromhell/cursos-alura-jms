package br.com.alura.jms.seletor;

import java.text.MessageFormat;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.alura.jms.factory.ConnectionFactoryHelper;
import br.com.alura.jms.factory.ContextFactory;
import br.com.alura.jms.factory.SessionFactory;

public class TesteProdutorTopicoSeletor {
	
	public static void main(String args[]) throws Exception {

		InitialContext context = ContextFactory.createContext();
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null);
		Session session = SessionFactory.createSessionFrom(connection);
		
		Destination topico = (Destination) context.lookup("loja");

		MessageProducer producer = session.createProducer(topico);

		String msgFmtStr = "<pedido><id>{0}</id></pedido>";
		MessageFormat msgFmt = new MessageFormat(msgFmtStr);
		
		for (int i = 1; i <= 4; i++) {
			Message message = session.createTextMessage(msgFmt.format(new Object[] {i})+"<e-book>FALSE</e-book>");
			message.setBooleanProperty("ebook", false);
			producer.send(message);
		}
		
		for (int i = 1; i <= 2; i++) {
			Message message = session.createTextMessage(msgFmt.format(new Object[] {i})+"<e-book>true</e-book>");
			message.setBooleanProperty("ebook", true);
			producer.send(message);
		}
		
		session.close();
		connection.close();
		context.close();

	}

}
