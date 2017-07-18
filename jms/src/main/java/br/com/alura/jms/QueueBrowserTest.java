package br.com.alura.jms;

import java.util.Enumeration;
import java.util.Scanner;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

public class QueueBrowserTest {
	
	public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        QueueConnectionFactory cf = (QueueConnectionFactory)ctx.lookup("ConnectionFactory");
        QueueConnection conexao = cf.createQueueConnection();
        conexao.start();

        QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) ctx.lookup("financeiro");
        QueueBrowser browser = sessao.createBrowser(fila);

        Enumeration msgs = browser.getEnumeration();
        
        while( msgs.hasMoreElements()) {
            System.out.println(msgs.nextElement());
        }
        
//        Enumeration msgs = browser.getEnumeration();
//        while (msgs.hasMoreElements()) { 
//            TextMessage msg = (TextMessage) msgs.nextElement(); 
//            System.out.println("Message: " + msg.getText()); 
//        }
        
        new Scanner(System.in).nextLine();

        browser.close();
        sessao.close();
        conexao.close();    
        ctx.close();
    }

}
