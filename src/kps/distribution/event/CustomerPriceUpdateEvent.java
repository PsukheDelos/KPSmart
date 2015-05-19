package kps.distribution.event;

public class CustomerPriceUpdateEvent extends DistributionNetworkEvent {
	public final String to;
	public final String from;
	public final String priority;
	public final double weightCost;
	public final double volumeCost;

	public CustomerPriceUpdateEvent(String to, String from, String priority,
			double weightCost, double volumeCost) {
		this.to = to;
		this.from = from;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
	}
}
