package kps.net.event;

public class NewUserResultEvent extends Event{
	
	public final boolean successful;
	
	public NewUserResultEvent(boolean successful){
		this.successful = successful;
	}

}
