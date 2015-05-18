package kps.net.event;

import kps.backend.users.User;

public class LoginResponseEvent extends Event{
	
	public final User user;
	
	public LoginResponseEvent(User user){
		this.user = user;
	}

}
