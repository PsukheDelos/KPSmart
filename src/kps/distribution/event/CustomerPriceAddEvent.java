package kps.distribution.event;

public class CustomerPriceAddEvent extends CustomerPriceEvent{

	private static final long serialVersionUID = 1L;

	public CustomerPriceAddEvent(String from, String to, String priority,
			double weightCost, double volumeCost) {
		super(from, to, priority, weightCost, volumeCost, "add");
	}

}
