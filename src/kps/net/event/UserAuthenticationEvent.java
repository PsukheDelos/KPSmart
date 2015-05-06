package kps.net.event;

import kps.backend.users.User;

public class UserAuthenticationEvent extends Event{
	
	public final User user;
	
	public UserAuthenticationEvent(User user){
		this.user = user;
	}

}
