package kps.tests.net;

import static org.junit.Assert.*;
import kps.interfaces.IMailClient;
import kps.net.client.Client;
import kps.net.event.DummyEvent;
import kps.net.server.Server;

import org.junit.Test;

public class ServerClientTests {
	
	
	private IMailClient client;
	
	private Server startupServer(){
		return new Server(new TestMailSystem());
	}
	
	private Client startupClient(){
		client = new TestMailClient();
		return new Client("127.0.0.1", client);
	}
	
	@Test
	public void SendingDummyEventShouldReturnDummyEvent() throws InterruptedException{
		Server server = startupServer();
		server.start();
		Client client = startupClient();
		DummyEvent de = new DummyEvent();
		
		client.sendEvent(de);
		// Wait for the event to move about our system, say, for a second
		Thread.sleep(1000);
		
		assertTrue(((TestMailClient)this.client).events.get(0) instanceof DummyEvent);
	}
	
	@Test
	public void SendingNonDummyEventShouldNotReturnADummyEvent(){
		
	}

}
