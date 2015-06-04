package kps.distribution.event;

import java.util.UUID;

public class CustomerPriceUpdateEvent extends DistributionNetworkEvent {
	public final String to;
	public final String from;
	public final String priority;
	public final double weightCost;
	public final double volumeCost;
	
	public final UUID id;

	public CustomerPriceUpdateEvent(String from, String to, String priority,
			double weightCost, double volumeCost) {
		this.from = from;
		this.to = to;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		
		this.id = UUID.randomUUID();
	}
}
