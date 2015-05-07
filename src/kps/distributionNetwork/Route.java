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
	
	public Route(){
		
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
	
	public boolean canShip(Mail mail){
		return mail.weight <= maxWeight
			&& mail.volume <= maxVolume;
	}
	
	public float getCost(Mail mail){
		return (mail.weight * weightCost)
			 + (mail.volume * volumeCost);
	}
}
