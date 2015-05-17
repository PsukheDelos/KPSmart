package kps.client.main;

import kps.net.client.Client;
import kps.net.event.DummyEvent;

public class BeginClient {
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		
		
		Client client = new Client("192.168.1.6");
		client.sendEvent(new DummyEvent());
		
	}

}
