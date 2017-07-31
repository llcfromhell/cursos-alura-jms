package br.com.alura.jms.log;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumidorMensagemLog {

	public static void main(String[] args) throws Exception {
		
		JmsSessionHelper helper = new JmsSessionHelper();
		
		MessageConsumer consumer = helper.createConsumer("log");
		
		consumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message){
            	TextMessage textMessage  = (TextMessage)message;
                try{
                    System.out.println(textMessage.getText());
                } catch(JMSException e){
                    e.printStackTrace();
                }
            }

        });
        
        new Scanner(System.in).nextLine();

		helper.quit();
	}
	
}
