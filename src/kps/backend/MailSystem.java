package kps.backend;

import java.util.ArrayList;
import java.util.List;

import kps.backend.users.User;
import kps.backend.users.UserRepository;
import kps.net.event.DummyEvent;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.UserAuthenticationEvent;

public class MailSystem {
	
	private List<User> loggedInUsers = new ArrayList<User>();
	
	public MailSystem(){
		
	}
	
	/**
	 * Kinda holds onto the idea that there is ALWAYS a return event to be made
	 * @param event
	 * @return
	 */
	public Event processEvent(Event event){
		
		Event returnEvent = null;
		
		if(event instanceof DummyEvent){
			System.out.println(((DummyEvent)event).message);
		}else if(event instanceof UserAuthenticationEvent){
			UserAuthenticationEvent auth = (UserAuthenticationEvent)event;
			System.out.println("Authenicating User: " + auth.username);
			User user = UserRepository.authenticateUser(auth.username, auth.password);
			returnEvent = new LoginResponseEvent(user);
		}
		return returnEvent;
		
	}
	
	public String toString(){
		return "[MAIL SYSTEM] ";
	}

}
