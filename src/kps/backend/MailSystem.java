package kps.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kps.backend.database.UserRepository;
import kps.backend.users.User;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.DistributionNetworkEvent;
import kps.distribution.event.MailDeliveryEvent;
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
			System.out.println(this + "Authenicating User: " + auth.username);
			User user = UserRepository.authenticateUser(auth.username, auth.password);
			returnEvent = new LoginResponseEvent(user);
		}else if(event instanceof DistributionNetworkEvent){
			DistributionNetworkEvent networkEvent = (DistributionNetworkEvent)event;

			returnEvent = network.processEvent(networkEvent);
			// Then, if it's an event we need a specific return from, set its UUID.
			if(event instanceof MailDeliveryEvent 
					&& returnEvent instanceof DeliveryEventResult){
				System.out.println("Adding UUID to return event for a MailDelivery");
				UUID clientUUID = ((MailDeliveryEvent)event).id;
				((DeliveryEventResult)returnEvent).id = clientUUID;
			}
		}
		return returnEvent;
	}
	
	public String toString(){
		return "[MAIL SYSTEM] ";
	}

}
