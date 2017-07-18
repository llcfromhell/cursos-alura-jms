package br.com.alura.jms.factory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;

public abstract class ConnectionFactoryHelper {

	public static Connection createConnectionFrom(InitialContext context, String clientId) throws Exception {

		// imports do package javax.jms
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.setClientID(clientId);
		connection.start();
		
		return connection;

	}
	
}
