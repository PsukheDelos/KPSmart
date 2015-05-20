package kps.distribution.event;

public class TransportCostUpdateEvent extends DistributionNetworkEvent {
	public final String company;
	public final String to;
	public final String from;
	public final String type;
	public final double weightCost;
	public final double volumeCost;
	public final double maxWeight;
	public final double maxVolume;
	public final double duration;
	public final double frequency;
	public final String day;

	public TransportCostUpdateEvent(String company, String to, String from, String type,
			double weightCost, double volumeCost, double maxWeight, double maxVolume,
			double duration, double frequency, String day) {
		this.company = company;
		this.to = to;
		this.from = from;
		this.type = type;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.duration = duration;
		this.frequency = frequency;
		this.day = day;
	}
}
