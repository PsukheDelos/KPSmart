package kps.frontend;

import kps.backend.users.User;
import kps.distribution.event.DeliveryEventResult;
import kps.frontend.gui.ClientFrame;
import kps.net.client.Client;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.UserAuthenticationEvent;

public class MailClient {
	
	private User currentUser;
	private Client client;
	private ClientFrame clientFrame;
	
	public MailClient(ClientFrame clientFrame){
		client = new Client("127.0.0.1", this);
		this.clientFrame = clientFrame;
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
		else if(e instanceof DeliveryEventResult){
			System.err.println("MAL CLEN: " + ((DeliveryEventResult)e).mailDelivery.cost);
//			LoginResponseEvent evt = (LoginResponseEvent)e;
//
//			System.out.println(this + "Response Recieved for " + evt.user.username);
//			setCurrentUser(evt.user);
		}
		
	}
	
	public void sendEvent(Event e) {
		client.sendEvent(e);
//		if(e instanceof LoginResponseEvent){
//			LoginResponseEvent evt = (LoginResponseEvent)e;
//
//			System.out.println(this + "Response Recieved for " + evt.user.username);
//			setCurrentUser(evt.user);
//		}
//		
//		return e;
	}
	
	public String toString(){
		return "[MAIL CLIENT] ";
	}

}
