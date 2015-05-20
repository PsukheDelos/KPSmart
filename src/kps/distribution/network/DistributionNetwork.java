package kps.distribution.network;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.DiscontinueEventResult;
import kps.distribution.event.DistributionNetworkEvent;
import kps.distribution.event.EventResult;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.SuccessEvent;
import kps.distribution.event.TransportCostUpdateEvent;
import kps.distribution.event.TransportDiscontinuedEvent;
import kps.distribution.exception.InvalidRouteException;
import kps.distribution.exception.PathNotFoundException;
import kps.distribution.pathFinder.Dijkstra;
import kps.distribution.pathFinder.Optimisation;
import kps.distribution.pathFinder.PathCondition;
import kps.distribution.pathFinder.PathFinder;

public class DistributionNetwork {
	private Map<String, Location> locations = new HashMap<String, Location>();
	private Set<Route> routes = new HashSet<Route>();
	private Map<String, Company> companies = new HashMap<String, Company>();
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

		if (!companies.containsValue(route.getCompany()))
			companies.put(route.getCompany().name, route.getCompany());

		routes.add(route);
		route.getOrigin().addRouteOut(route);
	}

	public void addRoutes(Collection<Route> routes) throws InvalidRouteException{
		for (Route route : routes){
			addRoute(route);
		}
	}
	
	public Company getOrAddCompany(String name){
		Company company = new Company(name);
		companies.put(name, company);
		return company;
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

	public Map<String, Company> getCompanies(){
		return this.companies;
	}

	public EventResult processEvent(DistributionNetworkEvent event) {
		if (event instanceof MailDeliveryEvent){
			MailDeliveryEvent mde = (MailDeliveryEvent)event;
			return processMailDeliveryEvent(mde);
		}
		else if (event instanceof CustomerPriceUpdateEvent){
			CustomerPriceUpdateEvent cpue = (CustomerPriceUpdateEvent)event;
			return processCustomerPriceUpdateEvent(cpue);
		}
		else if (event instanceof TransportCostUpdateEvent){
			TransportCostUpdateEvent tcue = (TransportCostUpdateEvent)event;
			return processTransportCostUpdateEvent(tcue);
		}
		else if (event instanceof TransportDiscontinuedEvent){
			TransportDiscontinuedEvent tde = (TransportDiscontinuedEvent)event;
			return processTransportDiscontinuedEvent(tde);
		}
		else{
			return new InvalidEventResult("Event type '" + event.getClass().getName() + "' not supported");
		}
	}

	private EventResult processTransportDiscontinuedEvent(TransportDiscontinuedEvent event) {
		Company company = companies.get(event.company);
		Location origin = locations.get(event.from);
		Location destination = locations.get(event.to);
		TransportType type = TransportType.fromString(event.type);

		Set<Route> routesToRemove = routes.stream()
			.filter(r -> r.getCompany().equals(company)
				&& r.getOrigin().equals(origin)
				&& r.getDestination().equals(destination)
				&& r.getType().equals(type))
			.collect(Collectors.toSet());

		if (routesToRemove.isEmpty()) return new InvalidEventResult("Could not find a matching Route");

		routes.remove(routesToRemove);
		return new DiscontinueEventResult("Route removed successfully");

	}

	private EventResult processTransportCostUpdateEvent(TransportCostUpdateEvent event) {
		// TODO
		return new InvalidEventResult("Not implemented");
	}

	private EventResult processCustomerPriceUpdateEvent(CustomerPriceUpdateEvent event) {
		//TODO
		return new InvalidEventResult("Not implemented");
	}

	private EventResult processMailDeliveryEvent(MailDeliveryEvent event) {
		Mail mail = new Mail(locations.get(event.from), locations.get(event.to),
				event.weight, event.volume, Priority.fromString(event.priority), event.day);
		try {
			return new DeliveryEventResult(deliver(mail));
		} catch (PathNotFoundException e) {
			return new InvalidEventResult("Could not find a path from origin to destination");
		}
	}
}
