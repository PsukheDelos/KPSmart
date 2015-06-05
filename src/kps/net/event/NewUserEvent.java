package kps.net.event;

import kps.backend.UserPermissions;

public class NewUserEvent extends Event{
	
	public final String username;
	public final String password;
	public final UserPermissions permissions;
	
	public NewUserEvent(String username, String password, UserPermissions permissions){
		this.username = username;
		this.password = password;
		this.permissions = permissions;
	}

}
