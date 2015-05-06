package kps.backend;

import kps.net.event.Event;

public class MailSystem {
	
	public MailSystem(){
		
	}
	
	/**
	 * Kinda holds onto the idea that there is ALWAYS a return event to be made
	 * @param event
	 * @return
	 */
	public Event processEvent(Event event){
		return event;
		
	}

}
