package kps.distribution.event;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "event")
@XmlType(propOrder = {"from", "to", "priority", "weight", "volume"})
public class MailDeliveryEvent extends DistributionNetworkEvent{
	private static final long serialVersionUID = 1L;
	@XmlTransient
	public final String day;
	public final String from;
	public final String to;
	public final double weight;
	public final double volume;
	
	@XmlTransient
	public final double price;
	
	public final String priority;

	@XmlAttribute
	public final String xmlType;
	
	@XmlAttribute
	public final String action;
	
	@XmlTransient
	public final UUID id;
	
	public MailDeliveryEvent(String day, String from, String to,
			double weight, double volume, double price, String priority){
		this.day = day;
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.volume = volume;
		this.price = price;
		this.priority = priority;
		
		this.action = "delivery";
		this.xmlType = "mail";
		this.id = UUID.randomUUID();
	}
	
	public MailDeliveryEvent(){
		this.day = "";
		this.from = "";
		this.to = "";
		this.weight = 0.00;
		this.volume = 0.00;
		this.price = 0.00;
		this.priority = "";
		
		this.action = "delivery";
		this.xmlType = "mail";
		this.id = UUID.randomUUID();
	}

	public String getDay() {
		return day;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public double getWeight() {
		return weight;
	}

	public double getVolume() {
		return volume;
	}

	public double getPrice() {
		return price;
	}

	public String getPriority() {
		return priority;
	}

	public String getXmlType() {
		return xmlType;
	}
	
	public String getAction() {
		return action;
	}

}
