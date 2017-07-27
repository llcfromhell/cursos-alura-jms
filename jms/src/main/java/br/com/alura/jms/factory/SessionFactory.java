package br.com.alura.jms.factory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.naming.InitialContext;

public abstract class SessionFactory {

	public static Session createSessionFrom(Connection connection) throws Exception {
		
		
		
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
	}
	
}
