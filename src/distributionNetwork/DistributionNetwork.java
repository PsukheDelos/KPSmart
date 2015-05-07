package distributionNetwork;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DistributionNetwork {
	private Map<String, Location> locations = new HashMap<String, Location>();
	private Set<Route> routes = new HashSet<Route>();
	private Set<Company> companies = new HashSet<Company>();
	
	public void addLocation(Location location){
		locations.put(location.getName(), location);
	}
	
	public void addRoute(Route route) throws InvalidRouteException{
		if (!getLocations().contains(route.getOrigin()))
			throw new InvalidRouteException("Origin location does not exist");
		if (!getLocations().contains(route.getDestination()))
			throw new InvalidRouteException("Destination location does not exist");
		if (!companies.contains(route.getCompany()))
			throw new InvalidRouteException("Company does not exist");
		if (routes.contains(route))
			throw new InvalidRouteException("Route already exists");
		
		routes.add(route);
		route.getOrigin().addRouteOut(route);
	}
	
	public Collection<Location> getLocations(){
		return this.locations.values();
	}
	
	public Map<String, Location> getLocationMap(){
		return locations;
	}
}
