package br.com.alura.jms.seletor;

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
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.alura.jms.factory.ConnectionFactoryHelper;
import br.com.alura.jms.factory.ContextFactory;
import br.com.alura.jms.factory.SessionFactory;

public class TesteConsumidorTopicoEstoqueSeletor2 {

	public static void main(String[] args) throws Exception{

		InitialContext context = ContextFactory.createContext();
		Connection connection = ConnectionFactoryHelper.createConnectionFrom(context, "estoque-seletor-2");
		Session session = SessionFactory.createSessionFrom(connection);
		
        Destination topico = (Destination) context.lookup("loja");
        
        String selectorStr = "ebook = true OR ebook IS NULL";
        
        MessageConsumer consumer = session.createDurableSubscriber( (Topic) topico, "sign selector 2", selectorStr, false);
        
        consumer.setMessageListener(new MessageListener(){

            @Override
            public void onMessage(Message message){
            	TextMessage textMessage  = (TextMessage) message;
                try{
                    System.out.println(textMessage.getText());
                } catch(JMSException e){
                    e.printStackTrace();
                }
            }

        });
        
        new Scanner(System.in).nextLine(); //parar o programa para testar a conexao

        session.close();
        
        connection.close();
        context.close();
	}
	
}
