package kps.distribution.event;

public class MailDeliveryEvent extends DistributionNetworkEvent{
	public final String day;
	public final String to;
	public final String from;
	public final double weight;
	public final double volume;
	public final String priority;

	public MailDeliveryEvent(String day, String to, String from,
			double weight, double volume, String priority){
		this.day = day;
		this.to = to;
		this.from = from;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
	}
}