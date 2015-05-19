package kps.distribution.network;

import java.util.HashSet;
import java.util.Set;

public class Location {
	public final String name;
	public final Double lon;
	public final Double lat;
	private Set<Route> routesOut = new HashSet<Route>();

	public Location(String name, double lon, double lat){
		this.name = name;
		this.lon = lon;
		this.lat = lat;
	}

	public String getName(){
		return this.name;
	}

	/**
	 * Adds a route to the routes out set in this location
	 * @param route The route to be added
	 * @return True if successfully added
	 */
	public boolean addRouteOut(Route route){
		return this.routesOut.add(route);
	}

	/**
	 * Removed a route from the routes out set in this location
	 * @param route The route to remove
	 * @return True if the route was removed successfully
	 */
	public boolean removeRouteOut(Route route){
		return this.routesOut.remove(route);
	}

	public Set<Route> getRoutesOut(){
		return new HashSet<Route>(this.routesOut);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return name != null && name.equals(((Location) obj).name);
	}
}
