package kps.distribution.event;

public class CustomerPriceUpdateEvent extends CustomerPriceEvent {

	private static final long serialVersionUID = 1L;
	
	public CustomerPriceUpdateEvent(String from, String to, String priority,
			double weightCost, double volumeCost) {
		super(from, to, priority, weightCost, volumeCost, "update");
	}

}
