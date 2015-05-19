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
}
