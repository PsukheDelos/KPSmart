package kps.net.client;

import java.util.List;

import kps.net.event.Event;

public class Client {
	
	private ClientToServerConnection connection;
	
	public Client(String ip){
		this.connection = new ClientToServerConnection(ip);
		connection.start();
	}
	
	public void update(){
		List<Event> events = connection.getUpdatesToBeActioned();
		// Iterate over, and deal with the client side events here, or offload them to the mailClient class
	}
	
	public void sendEvent(Event event){
		connection.send(event);
	}

}
