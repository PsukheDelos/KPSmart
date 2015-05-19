package kps.backend;

import java.util.ArrayList;
import java.util.List;

import kps.backend.database.UserRepository;
import kps.backend.users.User;
import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.TransportCostUpdateEvent;
import kps.distribution.event.TransportDiscontinuedEvent;
import kps.distribution.network.DistributionNetwork;
import kps.net.event.DummyEvent;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.UserAuthenticationEvent;

public class MailSystem {
	
	private List<User> loggedInUsers = new ArrayList<User>();
	private DistributionNetwork network = new DistributionNetwork();
	
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
		}else if(event instanceof MailDeliveryEvent){
			
		}else if(event instanceof CustomerPriceUpdateEvent){
			
		}else if(event instanceof TransportCostUpdateEvent){
			
		}else if(event instanceof TransportDiscontinuedEvent){
			
		}
		return returnEvent;
	}
	
	public String toString(){
		return "[MAIL SYSTEM] ";
	}

}
