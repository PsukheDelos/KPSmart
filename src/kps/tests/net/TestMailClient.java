package kps.tests.net;

import java.util.ArrayList;
import java.util.List;

import kps.interfaces.IMailClient;
import kps.net.event.Event;

public class TestMailClient implements IMailClient{
	
	public List<Event> events = new ArrayList<Event>();

	@Override
	public void processEvent(Event e) {
		events.add(e);
	}

}
