package kps.distributionNetwork;

import java.util.HashSet;
import java.util.Set;

public class Location {
	private String name;
	private Set<Route> routesOut = new HashSet<Route>();
	
	public Location(String name){
		this.name = name;
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
}
