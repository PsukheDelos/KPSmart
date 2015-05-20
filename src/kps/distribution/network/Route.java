package kps.distribution.network;

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
	private TransportType type;

	public Route(Location origin, Location destination, Company company,
			double weightCost, double volumeCost, double maxWeight,
			double maxVolume, double duration, double frequency, TransportType type) {
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
	}

	public Company getCompany(){
		return this.company;
	}

	public Location getOrigin(){
		return this.origin;
	}

	public Location getDestination(){
		return this.destination;
	}

	public double getDuration() {
		return duration;
	}

	public double getFrequency(){
		return this.frequency;
	}

	public TransportType getType(){
		return this.type;
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
}
