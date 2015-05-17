package kps.backend.users;

import java.io.Serializable;

import kps.backend.UserPermissions;

public class User implements Serializable{
	
	public final String username;
	public final UserPermissions permissions;
	
	public User(String username, UserPermissions permissions){
		this.username = username;
		this.permissions = permissions;
		
	}

}
