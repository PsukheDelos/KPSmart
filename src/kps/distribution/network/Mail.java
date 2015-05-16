package kps.distribution.network;

import java.util.Date;

public class Mail {
	public final Location origin;
	public final Location destination;

	public final float volume;
	public final float weight;

	public final String priority;
	public final Date sentDate;

	public Mail(Location origin, Location destination, float weight, float volume, String priority, Date sentDate){
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.sentDate = sentDate;
	}
}
