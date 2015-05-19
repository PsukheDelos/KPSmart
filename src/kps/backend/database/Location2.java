package kps.backend.database;

import java.io.Serializable;

import kps.backend.UserPermissions;

public class Location2 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public final String city;
	public final String country;
	public final Double lon;
	public final Double lat;
	
	public Location2(String city, String country, Double lon, Double lat){
		this.city = city;
		this.country = country;
		this.lon = lon;
		this.lat = lat;
	}

}
