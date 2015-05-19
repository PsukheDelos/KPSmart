package kps.distribution.network;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kps.distribution.exception.InvalidRouteException;
import kps.distribution.exception.PathNotFoundException;
import kps.distribution.pathFinder.Dijkstra;
import kps.distribution.pathFinder.Optimisation;
import kps.distribution.pathFinder.PathCondition;
import kps.distribution.pathFinder.PathFinder;

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

	/**
	 * Attempts to generate a path from the mail's origin to it's destination.
	 * - For priority items, the pathfinder will attempt to follow air routes to
	 *   the destination. If it cannot then it will accept land and sea routes.
	 * - For standard shipping, the pathfinder will simply look for the cheapest
	 *   path regardless of the type of route.
	 * @param mail The mail item to deliver
	 * @return A MailDelivery object containing the mail, the delivery path as a
	 *         List of Routes and the cost of the delivery.
	 * @throws PathNotFoundException If a valid path cannot be found
	 */
	public MailDelivery deliver(Mail mail) throws PathNotFoundException {
		switch (mail.priority){
		case DOMESTIC_AIR:
		case INTERNATIONAL_AIR:
			// attempt to find an Air only delivery path
			PathCondition airOnlyPath = new PathCondition(
				PathType.AIR_ONLY,
				Optimisation.LOWEST_COST
			);
			try{
				pathFinder = new Dijkstra(airOnlyPath);
				return pathFinder.getPath(mail);
			}catch (PathNotFoundException e){
				// if an air only path cannot be found, settle for combination of
				// land, sea and air
				PathCondition airLandSea = new PathCondition(
					PathType.AIR_SEA_LAND,
					Optimisation.LOWEST_COST
				);
				pathFinder = new Dijkstra(airLandSea);
				return pathFinder.getPath(mail);
			}
		case DOMESTIC_STANDARD:
		case INTERNATIONAL_STANDARD:
			// for standard shipping, land, sea and air are all okay, just find the cheapest
			PathCondition airLandSea = new PathCondition(
				PathType.AIR_SEA_LAND,
				Optimisation.LOWEST_COST
			);
			pathFinder = new Dijkstra(airLandSea);
			return pathFinder.getPath(mail);
		default:
			throw new InvalidParameterException("Unexpected priority");
		}
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
