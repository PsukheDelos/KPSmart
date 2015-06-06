package kps.distribution.event;

public class CustomerPriceRemoveEvent extends CustomerPriceEvent{

	private static final long serialVersionUID = 1L;
	
	public CustomerPriceRemoveEvent(String from, String to, String priority) {
		super(from, to, priority, 0, 0, "remove");
	}

}
