package kps.tests.net;

import kps.interfaces.IMailSystem;
import kps.net.event.DummyEvent;
import kps.net.event.Event;

public class TestMailSystem implements IMailSystem{

	@Override
	public Event processEvent(Event event) {
		if(event instanceof DummyEvent){
			System.out.println(((DummyEvent)event).message);
		}
		return event;
	}

}
