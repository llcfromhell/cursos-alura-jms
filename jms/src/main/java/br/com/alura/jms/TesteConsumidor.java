package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteConsumidor {
	
	public static void main(String[] args) throws Exception{

        InitialContext context = new InitialContext(); 

        //imports do package javax.jms
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();

        new Scanner(System.in).nextLine(); //parar o programa para testar a conexao

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        Message message = consumer.receive();
        System.out.println("Recebendo msg: "+ message);

        session.close();
        
        connection.close();
        context.close();
	}

}
