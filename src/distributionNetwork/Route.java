package distributionNetwork;

public class Route {
	private Location origin;
	private Location destination;
	private Company company;
	
	// cost per gram
	private float weightCost;
	// cost per cubic centimeter
	private float volumeCost;
	
	private float maxWeight;
	private float maxVolume;
	
	private float duration;
	private float frequency;
	private String type;
	
	public Route(){
		
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
