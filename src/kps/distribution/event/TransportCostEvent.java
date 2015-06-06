package kps.distribution.event;

import java.util.UUID;

//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;
//
//@XmlRootElement(name = "price")
//@XmlType(propOrder = {"to", "from", "priority", "weightCost", "volumeCost", "action"})
public abstract class TransportCostEvent extends DistributionNetworkEvent {
	private static final long serialVersionUID = 1L;
	
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
	
	public final String action;
	public final UUID id;


	public TransportCostEvent(String company, String from, String to, String type,
			double weightCost, double volumeCost, double maxWeight, double maxVolume,
			double duration, double frequency, String day, String action) {
	
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
		
		this.action = action;
		this.id = UUID.randomUUID();
	}
}