package kps.distribution.event;

public class DiscontinueEventResult extends EventResult {
	private static final long serialVersionUID = 1L;
	
	public final String message;
	
	public DiscontinueEventResult(String msg) {
		this.message = msg;
	}

}
