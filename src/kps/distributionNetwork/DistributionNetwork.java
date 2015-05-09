package kps.distributionNetwork;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kps.dijkstra.Dijkstra;

public class DistributionNetwork {
	private Map<String, Location> locations = new HashMap<String, Location>();
	private Set<Route> routes = new HashSet<Route>();
	private Set<Company> companies = new HashSet<Company>();
	private PathFinder pathFinder = new Dijkstra();
	
	public void addLocation(Location location){
		locations.put(location.getName(), location);
	}
	
	public void addRoute(Route route) throws InvalidRouteException{
		if (!getLocations().contains(route.getOrigin()))
			throw new InvalidRouteException("Origin location does not exist");
		if (!getLocations().contains(route.getDestination()))
			throw new InvalidRouteException("Destination location does not exist");
		if (!companies.contains(route.getCompany()))
			companies.add(route.getCompany());
		if (routes.contains(route))
			throw new InvalidRouteException("Route already exists");
		
		routes.add(route);
		route.getOrigin().addRouteOut(route);
	}
	
	public MailDelivery deliver(Mail mail){
		return pathFinder.getPath(mail);
	}
	
	public Set<Location> getLocations(){
		return new HashSet<Location>(this.locations.values());
	}
	
	public Map<String, Location> getLocationMap(){
		return locations;
	}
	
	public Set<Route> getRoutes(){
		return this.routes;
	}
	
	public Set<Company> getCompanies(){
		return this.companies;
	}
}
