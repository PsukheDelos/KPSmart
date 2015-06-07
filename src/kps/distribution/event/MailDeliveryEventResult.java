package kps.distribution.event;

public class MailDeliveryEventResult extends EventResult {
	private static final long serialVersionUID = 1L;
	
	public final String day;
	public final String origin;
	public final String destination;
	public final Double weight;
	public final Double volume;
	public final String priority;
	public final Double price;
	public final Double cost;
	public final Double time;
	
	public MailDeliveryEventResult(String day, String origin,
			String destination, Double weight, Double volume, String priority,
			Double price, Double cost, Double time) {
		super();
		this.day = day;
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.price = price;
		this.cost = cost;
		this.time = time;
	}
	
//	public MailDeliveryEventResult(String day, String origin, String destination, Double weight, 
//			Double volume, String priority, Double price, Double cost, Double time){
//		this.day = day;
//		this.
//	}
}
