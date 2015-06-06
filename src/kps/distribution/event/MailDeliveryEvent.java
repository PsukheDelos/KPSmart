package kps.distribution.event;

import java.util.UUID;

public class MailDeliveryEvent extends DistributionNetworkEvent{
	private static final long serialVersionUID = 1L;
	public final String day;
	public final String from;
	public final String to;
	public final double weight;
	public final double volume;
	public final String priority;

	public final UUID id;
	
	public MailDeliveryEvent(String day, String from, String to,
			double weight, double volume, String priority){
		this.day = day;
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		
		this.id = UUID.randomUUID();
	}
}
