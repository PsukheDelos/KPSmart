package kps.distribution.network;

import java.util.Date;

public class Mail {
	public final Location origin;
	public final Location destination;

	public final double volume;
	public final double weight;

	public final Priority priority;
	public final Date sentDate;

	public Mail(Location origin, Location destination, double weight, double volume, Priority priority, Date sentDate){
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.sentDate = sentDate;
	}
}
