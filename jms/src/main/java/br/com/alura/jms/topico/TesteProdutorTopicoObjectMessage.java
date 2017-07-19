package br.com.alura.jms.topico;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.alura.jms.factory.ConnectionFactoryHelper;
import br.com.alura.jms.factory.ContextFactory;
import br.com.alura.jms.factory.SessionFactory;
import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopicoObjectMessage {
	
	public static void main(String args[]) throws Exception {

		InitialContext context = ContextFactory.createContext();
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
		Session session = SessionFactory.createSessionFrom(connection);
		
		try {
			Destination topico = (Destination) context.lookup("loja");

			MessageProducer producer = session.createProducer(topico);

			Pedido pedido = new PedidoFactory().geraPedidoComValores();

			StringWriter writer = new StringWriter();
			JAXB.marshal(pedido, writer);
			String xml = writer.toString();

			Message message = session.createTextMessage(xml);
			
			producer.send(message);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			session.close();
			connection.close();
			context.close();
		}
		
		

	}

}
