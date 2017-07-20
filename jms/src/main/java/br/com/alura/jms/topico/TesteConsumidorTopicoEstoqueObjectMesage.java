package br.com.alura.jms.topico;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.alura.jms.factory.ConnectionFactoryHelper;
import br.com.alura.jms.factory.ContextFactory;
import br.com.alura.jms.factory.SessionFactory;
import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopicoEstoqueObjectMesage {

	public static void main(String[] args) throws Exception{

		InitialContext context = ContextFactory.createContext();
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
		Session session = SessionFactory.createSessionFrom(connection);
		
        try {
        	
			Destination topico = (Destination) context.lookup("loja");
			MessageConsumer consumer = session.createDurableSubscriber( (Topic) topico, "sign");

			System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
			
			consumer.setMessageListener(new MessageListener(){

			    @Override
			    public void onMessage(Message message){
			    	Object objectMessage = (ObjectMessage) message;
			        try{
			            System.out.println(objectMessage);
			        } catch(Exception e){
			            e.printStackTrace();
			        }
			    }

			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			new Scanner(System.in).nextLine(); //parar o programa para testar a conexao

	        session.close();
	        
	        connection.close();
	        context.close();
		}
        
        
	}
	
}
