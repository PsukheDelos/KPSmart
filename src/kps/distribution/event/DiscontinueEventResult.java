package kps.distribution.event;

public class DiscontinueEventResult extends EventResult {

	public final String message;
	
	public DiscontinueEventResult(String msg) {
		this.message = msg;
	}

}
