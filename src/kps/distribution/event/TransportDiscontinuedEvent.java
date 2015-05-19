package kps.distribution.event;

public class TransportDiscontinuedEvent extends DistributionNetworkEvent {
	public final String company;
	public final String to;
	public final String from;
	public final String type;

	public TransportDiscontinuedEvent(String company, String to, String from, String type) {
		this.company = company;
		this.to = to;
		this.from = from;
		this.type = type;
	}
}
