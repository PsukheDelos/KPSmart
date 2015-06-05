package kps.frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.JOptionPane;

import kps.backend.users.User;
import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.PriceUpdateEventResult;
import kps.frontend.gui.ClientFrame;
import kps.net.client.Client;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.NewUserResultEvent;
import kps.net.event.UserAuthenticationEvent;

public class MailClient {
	
	private User currentUser;
	private Client client;
	private ClientFrame clientFrame;
	
	private Map<UUID, Event> awaitingResponse;
	
	public MailClient(ClientFrame clientFrame){
		client = new Client("127.0.0.1", this);
		this.clientFrame = clientFrame;
		
		awaitingResponse = new HashMap<UUID, Event>();
		
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void authenticateUser(String username, String password) {
		client.sendEvent(new UserAuthenticationEvent(username, password));
	}
	
	public void update(){
		client.update();
	}

	public void processEvent(Event e) {
		if(e instanceof LoginResponseEvent){
			LoginResponseEvent evt = (LoginResponseEvent)e;

			System.out.println(this + "Response Recieved for " + evt.user.username);
			setCurrentUser(evt.user);
		}
		else if(e instanceof NewUserResultEvent){
			JOptionPane.showMessageDialog(clientFrame, "User Added Successfully");
		}
		else if(e instanceof DeliveryEventResult){
			System.out.println("Received Return for DeliveryEventResult");
			if(awaitingResponse.containsKey(((DeliveryEventResult) e).id)){
				System.out.println("DeliveryEventResult Success!");
			}
			// Then you know the key is in there, and since it is stored with the corresponding Event, you know what you sent.
			// Maybe pass this to a method somewhere, and make sure that you remove it from the map once done. Just to avoid collisions.
			System.err.println(this + "" + ((DeliveryEventResult)e).mailDelivery.cost);
		}else if(e instanceof PriceUpdateEventResult){
//			System.out.println("Recieved Return for PriceUpdateEvent" + ((PriceUpdateEventResult)e).id);
			if(awaitingResponse.containsKey(((PriceUpdateEventResult)e).id)){
				clientFrame.updatePrice();
			}
		}
		
	}
	
	public void sendEvent(Event e) {
		// Here we will add an UUID to any Event you want
		// For now, I'll add it to the Mail Delivery Event
		// Look in MailSystem for the response method.
		if(e instanceof MailDeliveryEvent){
			System.out.println("Adding Awaiting Delivery for id: " + ((MailDeliveryEvent)e).id);
			awaitingResponse.put(((MailDeliveryEvent) e).id, (MailDeliveryEvent)e);
		}else if(e instanceof CustomerPriceUpdateEvent){
			System.out.println("Adding Price Updater Event awaiting return: " + ((CustomerPriceUpdateEvent)e).id);
			awaitingResponse.put(((CustomerPriceUpdateEvent) e).id, (CustomerPriceUpdateEvent)e);
		}
		
		
		client.sendEvent(e);
	}
	
	public String toString(){
		return "[MAIL CLIENT] ";
	}

}
