package kps.distribution.network;

public class Mail {
	public final Location origin;
	public final Location destination;

	public final double volume;
	public final double weight;

	public final Priority priority;
	public final String day;

	public Mail(Location origin, Location destination, double weight, double volume, Priority priority, String day){
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.day = day;
	}
}
