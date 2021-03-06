package kps.net.server;

import java.io.IOException;
import java.util.List;

import kps.interfaces.IGlobalEvent;
import kps.interfaces.IMailSystem;
import kps.net.event.Event;

public class UpdateThread extends Thread{
	
	Server server;
	
	public UpdateThread(Server server, IMailSystem mailSystem){
		this.server = server;
		setDaemon(true);
		start();
	}
	
	public void run(){
		while(true){
			
			Update update = server.popEvent();
			Event event = server.processEvent(update.event);
			
			if(event instanceof IGlobalEvent){
				sendAllClients(event);
			}
			else if(event != null){
				sendClient(event, server.getConnection(update.client));
			}
			
			// Here, we should send the return event;
			// Should really be only to a single client, but the functionality is now there.
		}
	}
	
	// -- METHODS FOR SENDING INFORMATION TO CLIENTS

	/**
	 *  Sends an event to a specified players client
	 * @param event Event to send
	 * @param player Player wrapper containing the client information
	 */
	public void sendClient(Event event, ServerToClientConnection player){
		try {
			player.out.reset();
			player.out.writeObject(event);
			player.out.flush();
		} catch (IOException e) {
			System.err.println("[ERROR] Unable to find Client! Will Remove from Server");
			server.removeConnection(player);
		}
	}

	/**
	 * Sends event to every client.
	 * @param event
	 */
	public void sendAllClients(Event event){
		for(ServerToClientConnection player : server.getClients().values()){
			sendClient(event, player);
		}
	}

	/**
	 * Sends events to every client, except certain players.
	 * @param event Event to send
	 * @param p Players to be ignored.
	 */

	public void sendAllClients(Event event, List<ServerToClientConnection> exceptions){
		for(ServerToClientConnection player : server.getClients().values()){
			if(exceptions.contains(player)) continue;
			sendClient(event, player);
		}
	}
	
	
	
	

}
