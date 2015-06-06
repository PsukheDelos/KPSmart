package kps.tests.net;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import kps.interfaces.IMailClient;
import kps.net.client.Client;
import kps.net.event.DummyEvent;
import kps.net.event.Event;
import kps.net.server.Server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerClientTests {
	
	
	private static IMailClient mailClient;
	private static Server server;
	private static Client client;
	
	@BeforeClass
	public static void setup(){
		server = startupServer();
		server.start();
		client = startupClient();
	}
	
	@AfterClass
	public static void classShutDown() throws InterruptedException, IOException{
		server.shutdown();
		server.join(1000);
	}
		
	private static Server startupServer(){
		return new Server(new TestMailSystem());
	}
	
	private static Client startupClient(){
		mailClient = new TestMailClient();
		return new Client("127.0.0.1", mailClient);
	}
	
	@After
	public void shutDown(){
		((TestMailClient)mailClient).events = new ArrayList<Event>();
	}
	
	@Test
	public void SendingDummyEvent_ShouldReturnDummyEvent() throws InterruptedException{

		DummyEvent de = new DummyEvent();
		
		client.sendEvent(de);
		// Wait for the event to move about our system, say, for a second
		Thread.sleep(1000);
		
		assertTrue(((TestMailClient)this.mailClient).events.get(0) instanceof DummyEvent);
	}
	
	@Test
	public void SendingNonDummyEvent_ShouldNotReturnDummyEvent() throws InterruptedException{
		Event e = new TestEvent();
		client.sendEvent(e);
		// Wait for the event to move about our system, say, for a second
		Thread.sleep(1000);
		assertFalse(((TestMailClient)this.mailClient).events.get(0) instanceof DummyEvent);
	}

}
