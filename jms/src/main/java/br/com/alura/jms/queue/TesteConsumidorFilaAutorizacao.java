package br.com.alura.jms.queue;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import br.com.alura.jms.factory.ConnectionFactoryHelper;
import br.com.alura.jms.factory.ContextFactory;

public class TesteConsumidorFilaAutorizacao {
	
	public static void main(String[] args) throws Exception{

        InitialContext context = ContextFactory.createContext();
        Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, null, "admin", "senha");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        try {
			Destination fila = (Destination) context.lookup("financeiro");
			MessageConsumer consumer = session.createConsumer(fila);

			consumer.setMessageListener(new MessageListener(){

			    @Override
			    public void onMessage(Message message){
			    	TextMessage textMessage  = (TextMessage)message;
			        try{
			            System.out.println(textMessage.getText());
			        } catch(JMSException e){
			            e.printStackTrace();
			        }
			    }

			});
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			new Scanner(System.in).nextLine(); //parar o programa para testar a conexao
			session.close();
	        connection.close();
	        context.close();
		}

	}

}
