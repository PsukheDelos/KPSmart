package kps.distribution.network;

import javax.xml.bind.annotation.XmlType;

import kps.distribution.event.TransportCostUpdateEvent;

@XmlType(propOrder = {"company", "origin", "destination", "maxWeight", "maxVolume", "weightCost", "volumeCost", "duration", "frequency", "day", "type"})
public class Route {
	private Location origin;
	private Location destination;
	private Company company;

	// cost per gram
	private double weightCost;
	// cost per cubic centimeter
	private double volumeCost;

	// max weight in grams
	private double maxWeight;
	//max volume in cubic centimeters
	private double maxVolume;

	private double duration;
	private double frequency;
	private String day;
	private TransportType type;

	public Route(Location origin, Location destination, Company company,
			double weightCost, double volumeCost, double maxWeight, double maxVolume,
			double duration, double frequency, TransportType type, String day) {
		this.origin = origin;
		this.destination = destination;
		this.company = company;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.duration = duration;
		this.frequency = frequency;
		this.type = type;
		this.day = day;
	}

	public Location getOrigin(){
		return this.origin;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public Location getDestination(){
		return this.destination;
	}
	
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	
	public Company getCompany(){
		return this.company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public double getWeightCost() {
		return weightCost;
	}

	public void setWeightCost(double weightCost) {
		this.weightCost = weightCost;
	}

	public double getVolumeCost() {
		return volumeCost;
	}

	public void setVolumeCost(double volumeCost) {
		this.volumeCost = volumeCost;
	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public double getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(double maxVolume) {
		this.maxVolume = maxVolume;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public double getFrequency(){
		return this.frequency;
	}
	
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	public String getDay(){
		return this.day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public TransportType getType(){
		return this.type;
	}
	
	public void setType(TransportType type) {
		this.type = type;
	}

	public boolean canShip(Mail mail){
		return mail.weight <= maxWeight
			&& mail.volume <= maxVolume;
	}

	public double getCost(Mail mail){
		return (mail.weight * weightCost)
			 + (mail.volume * volumeCost);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void update(TransportCostUpdateEvent event) {
		this.weightCost = event.weightCost;
		this.volumeCost = event.volumeCost;
		this.maxWeight = event.maxWeight;
		this.maxVolume = event.maxVolume;
		this.duration = event.duration;
		this.frequency = event.frequency;
		this.day = event.day;
	}
}
