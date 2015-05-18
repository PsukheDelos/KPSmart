package kps.frontend;

import kps.backend.users.User;
import kps.net.client.Client;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.UserAuthenticationEvent;

public class MailClient {
	
	private User currentUser;
	private Client client;
	
	public MailClient(){
		client = new Client("127.0.0.1", this);
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
			setCurrentUser(evt.user);
		}
		
	}

}
