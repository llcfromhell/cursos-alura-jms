package br.com.alura.jms.factory;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class ContextFactory {
	
	public static InitialContext createContext() throws NamingException {
		
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");

		return new InitialContext(properties);
		
	}

}
