package kps.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kps.backend.database.UserRepository;
import kps.backend.users.DuplicateUserException;
import kps.backend.users.User;
import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.DistributionNetworkEvent;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.PriceUpdateEventResult;
import kps.distribution.network.DistributionNetwork;
import kps.net.event.DummyEvent;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.NewUserEvent;
import kps.net.event.NewUserResultEvent;
import kps.net.event.RemoveUserEvent;
import kps.net.event.RemoveUserResultEvent;
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
		}else if(event instanceof NewUserEvent){
			
			boolean successful = true;
			
			NewUserEvent nue = (NewUserEvent)event;
			try { UserRepository.registerNewUser(nue.username, nue.password, nue.permissions); } catch (DuplicateUserException e) { successful = false;}
			returnEvent = new NewUserResultEvent(successful);
		}else if(event instanceof RemoveUserEvent){
			RemoveUserEvent rue = (RemoveUserEvent) event;
			UserRepository.removeUser(rue.username);
			returnEvent = new RemoveUserResultEvent();
		}else if(event instanceof DistributionNetworkEvent){
			DistributionNetworkEvent networkEvent = (DistributionNetworkEvent)event;

			returnEvent = network.processEvent(networkEvent);
			// Then, if it's an event we need a specific return from, set its UUID.
			if(event instanceof MailDeliveryEvent 
					&& returnEvent instanceof DeliveryEventResult){
				System.out.println("Adding UUID to return event for a MailDelivery");
				UUID clientEventUUID = ((MailDeliveryEvent)event).id;
				((DeliveryEventResult)returnEvent).id = clientEventUUID;
			}else if(event instanceof CustomerPriceUpdateEvent 
					&& returnEvent instanceof PriceUpdateEventResult){
				UUID clientEventUUID = ((CustomerPriceUpdateEvent)event).id;
				System.out.println("Adding UUID to return event for a CustomerPriceUpdate: " + clientEventUUID);
				((PriceUpdateEventResult)returnEvent).id = clientEventUUID;
			}
		}
		return returnEvent;
	}
	
	public String toString(){
		return "[MAIL SYSTEM] ";
	}

}
