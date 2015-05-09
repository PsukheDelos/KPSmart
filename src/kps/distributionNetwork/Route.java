package kps.distributionNetwork;

public class Route {
	private Location origin;
	private Location destination;
	private Company company;
	
	// cost per gram
	private float weightCost;
	// cost per cubic centimeter
	private float volumeCost;
	
	// max weight in grams
	private float maxWeight;
	//max volume in cubic centimeters
	private float maxVolume;
	
	private float duration;
	private float frequency;
	private String type;

	public Route(Location origin, Location destination, Company company,
			float weightCost, float volumeCost, float maxWeight,
			float maxVolume, float duration, float frequency, String type) {
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

	public float getDuration() {
		return duration;
	}

	public float getFrequency(){
		return this.frequency;
	}

	public String getType(){
		return this.type;
	}

	public boolean canShip(Mail mail){
		return mail.weight <= maxWeight
			&& mail.volume <= maxVolume;
	}

	public float getCost(Mail mail){
		return (mail.weight * weightCost)
			 + (mail.volume * volumeCost);
	}
}
