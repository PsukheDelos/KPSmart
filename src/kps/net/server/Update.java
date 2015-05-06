package kps.net.server;

import kps.net.event.Event;

public class Update {
	
	public final int client;
	public final Event event;
	
	public Update(int client, Event event){
		this.client = client;
		this.event = event;
	}

}
