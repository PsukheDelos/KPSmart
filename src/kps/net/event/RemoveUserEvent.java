package kps.net.event;

public class RemoveUserEvent extends Event{
	
	public final String username;
	
	public RemoveUserEvent(String username){
		this.username = username;
	}

}
