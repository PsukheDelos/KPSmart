package kps.net.client;

import java.util.List;

import kps.frontend.MailClient;
import kps.net.event.Event;

public class Client {
	
	private ClientToServerConnection connection;
	private MailClient mailClient;
	
	public Client(String ip, MailClient mailClient){
		this.connection = new ClientToServerConnection(ip);
		this.mailClient = mailClient;
		connection.start();
	}
	
	public void update(){
		List<Event> events = connection.getUpdatesToBeActioned();
		// Iterate over, and deal with the client side events here, or offload them to the mailClient class
		for(Event e : events){
			mailClient.processEvent(e);
		}
	}
	
	public ClientToServerConnection getConnection(){
		return connection;
	}
	
	public void sendEvent(Event event){
		connection.send(event);
	}

}
