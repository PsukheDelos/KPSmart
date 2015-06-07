package kps.distribution.event;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "event")
@XmlType(propOrder = { "action", "to", "from", "type", "weightCost", "volumeCost"})
public abstract class TransportCostEvent extends DistributionNetworkEvent {
	
	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	public final String company;
	public final String to;
	public final String from;
	public final String type;
	public final double weightCost;
	public final double volumeCost;
	@XmlTransient
	public final double maxWeight;
	@XmlTransient
	public final double maxVolume;
	@XmlTransient
	public final double duration;
	@XmlTransient
	public final double frequency;
	@XmlTransient
	public final String day;
	
	@XmlAttribute
	public final String xmlType;
	
	@XmlAttribute
	public final String action;
	
	@XmlTransient
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
		this.xmlType = "cost";
		this.id = UUID.randomUUID();
	}
	
	public TransportCostEvent() {
	
		this.company = "";
		this.to = "";
		this.from = "";
		this.type = "";
		this.weightCost = 0.00;
		this.volumeCost = 0.00;
		this.maxWeight = 0.00;
		this.maxVolume = 0.00;
		this.duration = 0.00;
		this.frequency = 0.00;
		this.day = "";
		
		this.action = "";
		this.xmlType = "cost";
		this.id = UUID.randomUUID();
	}
	
	public String getCompany() {
		return company;
	}


	public String getTo() {
		return to;
	}


	public String getFrom() {
		return from;
	}


	public String getType() {
		return type;
	}


	public double getWeightCost() {
		return weightCost;
	}


	public double getVolumeCost() {
		return volumeCost;
	}


	public double getMaxWeight() {
		return maxWeight;
	}


	public double getMaxVolume() {
		return maxVolume;
	}


	public double getDuration() {
		return duration;
	}


	public double getFrequency() {
		return frequency;
	}


	public String getDay() {
		return day;
	}

	public String getXmlType() {
		return xmlType;
	}

	public String getAction() {
		return action;
	}

}