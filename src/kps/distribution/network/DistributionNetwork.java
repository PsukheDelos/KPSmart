package kps.distribution.network;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kps.distribution.pathFinder.Dijkstra;
import kps.distribution.pathFinder.Optimisation;
import kps.distribution.pathFinder.PathCondition;
import kps.distribution.pathFinder.PathFinder;
import kps.distribution.pathFinder.PathNotFoundException;

public class DistributionNetwork {
	private Map<String, Location> locations = new HashMap<String, Location>();
	private Set<Route> routes = new HashSet<Route>();
	private Set<Company> companies = new HashSet<Company>();
	private PathFinder pathFinder;

	public void addLocation(Location location){
		locations.put(location.getName(), location);
	}

	public void addLocations(Collection<Location> locations){
		for (Location location : locations){
			addLocation(location);
		}
	}

	public void addRoute(Route route) throws InvalidRouteException{
		if (!getLocations().contains(route.getOrigin()))
			throw new InvalidRouteException("Origin location does not exist");
		if (!getLocations().contains(route.getDestination()))
			throw new InvalidRouteException("Destination location does not exist");
		if (routes.contains(route))
			throw new InvalidRouteException("Route already exists");

		if (!companies.contains(route.getCompany()))
			companies.add(route.getCompany());

		routes.add(route);
		route.getOrigin().addRouteOut(route);
	}

	public void addRoutes(Collection<Route> routes) throws InvalidRouteException{
		for (Route route : routes){
			addRoute(route);
		}
	}

	public MailDelivery deliver(Mail mail) throws PathNotFoundException {
		//TODO: determine cost limit and time limit based on mail.priority
		PathCondition pathCondition = new PathCondition(
			PathCondition.NO_COST_LIMIT,
			PathCondition.NO_TIME_LIMIT,
			Optimisation.LOWEST_COST
		);
		pathFinder = new Dijkstra(pathCondition);
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
