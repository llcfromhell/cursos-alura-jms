

import java.text.MessageFormat;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ProdutorMensagemLog {

	public static void main(String args[]) throws Exception {

//		InitialContext context = new InitialContext();

		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");

		InitialContext context = new InitialContext(properties);
		
		// imports do package javax.jms
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");

		MessageProducer producer = session.createProducer(fila);

		String msgFmtStr = "<pedido><id>{0}</id></pedido>";
		MessageFormat msgFmt = new MessageFormat(msgFmtStr);
		
		for (int i = 1; i <= 2; i++) {
			
			Message message = session.createTextMessage(msgFmt.format(new Object[] {i}));
//			Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			producer.send(message);
		}
		
		Message message = session.createTextMessage("");
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();

	}

}
