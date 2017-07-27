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

import br.com.alura.jms.factory.ConnectionFactoryHelper;
import br.com.alura.jms.factory.ContextFactory;
import br.com.alura.jms.factory.SessionFactory;

public class TesteProdutorTopicoAutorizacao {
	
	public static void main(String args[]) throws Exception {

		InitialContext context = ContextFactory.createContext();
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
		Session session = SessionFactory.createSessionFrom(connection);
		
		try {
			
			Destination topico = (Destination) context.lookup("loja");

			MessageProducer producer = session.createProducer(topico);

			String msgFmtStr = "<pedido><id>{0}</id></pedido>";
			MessageFormat msgFmt = new MessageFormat(msgFmtStr);
			
			for (int i = 1; i <= 2; i++) {
				Message message = session.createTextMessage(msgFmt.format(new Object[] {i}));
				producer.send(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
			connection.close();
			context.close();
		}

	}

}
