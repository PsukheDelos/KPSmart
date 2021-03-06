package kps.distribution.event;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "event")
@XmlType(propOrder = {"to", "from", "priority", "weightCost", "volumeCost", "action"})
public class CustomerPriceEvent extends DistributionNetworkEvent {
	private static final long serialVersionUID = 1L;

	public final String to;
	public final String from;
	public final String priority;
	public final double weightCost;
	public final double volumeCost;
	
	
	@XmlAttribute
	public final String xmlType;
	
	@XmlAttribute
	public final String action;

	@XmlTransient
	public final UUID id;

	public CustomerPriceEvent(String from, String to, String priority,
			double weightCost, double volumeCost, String action) {
		this.from = from;
		this.to = to;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		
		this.action = action;
		this.xmlType = "price";
		this.id = UUID.randomUUID();
	}
	
	public CustomerPriceEvent(){
		this.from = "";
		this.to = "";
		this.priority = "";
		this.weightCost = 0.00;
		this.volumeCost = 0.00;
		this.action = "";
		this.xmlType = "price";
		
		this.id = UUID.randomUUID();
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public String getPriority() {
		return priority;
	}

	public double getWeightCost() {
		return weightCost;
	}

	public double getVolumeCost() {
		return volumeCost;
	}
	
	public String getXmlType() {
		return xmlType;
	}
	
	public String getAction() {
		return action;
	}

}
