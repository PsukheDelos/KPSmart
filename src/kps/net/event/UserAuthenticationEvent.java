package kps.net.event;

import kps.backend.users.User;

public class UserAuthenticationEvent extends Event{
	
	public final String username;
	public final String password;
	
	public UserAuthenticationEvent(String username, String password){
		this.username = username;
		this.password = password;
	}

}
