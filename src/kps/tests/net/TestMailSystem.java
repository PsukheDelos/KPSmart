package kps.tests.net;

import kps.backend.database.UserRepository;
import kps.backend.users.DuplicateUserException;
import kps.interfaces.IMailSystem;
import kps.net.event.DummyEvent;
import kps.net.event.Event;
import kps.net.event.NewUserEvent;
import kps.net.event.NewUserResultEvent;
import kps.net.event.RemoveUserEvent;
import kps.net.event.RemoveUserResultEvent;

public class TestMailSystem implements IMailSystem{

	@Override
	public Event processEvent(Event event) {
		if(event instanceof DummyEvent){
			System.out.println(((DummyEvent)event).message);
		}else if(event instanceof NewUserEvent){
			boolean successful = true;
			NewUserEvent nue = (NewUserEvent)event;
			try { UserRepository.registerNewUser(nue.username, nue.password, nue.permissions); } catch (DuplicateUserException e) { successful = false;}
			event = new NewUserResultEvent(successful);
		}else if(event instanceof RemoveUserEvent){
			UserRepository.removeUser(((RemoveUserEvent)event).username);
			event = new RemoveUserResultEvent();
		}
		return event;
	}

}
