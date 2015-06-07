package kps.distribution.network;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.*;

import kps.backend.database.LocationRepository;
import kps.backend.database.MailRepository;
import kps.backend.database.PriceRepository;
import kps.backend.database.CostRepository;
import kps.distribution.event.LocationAddEvent;
import kps.distribution.event.LocationEvent;
import kps.distribution.event.LocationEventResult;
import kps.distribution.event.LocationRemoveEvent;
import kps.distribution.event.LocationUpdateEvent;
import kps.distribution.event.MailDeliveryEventResult;
import kps.distribution.event.TransportCostAddEvent;
import kps.distribution.event.TransportCostEventResult;
import kps.distribution.event.CustomerPriceAddEvent;
import kps.distribution.event.CustomerPriceEvent;
import kps.distribution.event.CustomerPriceRemoveEvent;
import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.DistributionNetworkEvent;
import kps.distribution.event.EventResult;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.CustomerPriceEventResult;
import kps.distribution.event.TransportCostEvent;
import kps.distribution.event.TransportCostRemoveEvent;
import kps.distribution.event.TransportCostUpdateEvent;
import kps.distribution.event.UpdateTableEvent;
import kps.distribution.event.UpdateTableEventResult;
import kps.distribution.event.UpdateTablePriceEvent;
import kps.distribution.event.UpdateTableRouteEvent;
import kps.distribution.event.UpdateTableUserEvent;
import kps.distribution.exception.InvalidRouteException;
import kps.distribution.exception.PathNotFoundException;
import kps.distribution.pathFinder.Dijkstra;
import kps.distribution.pathFinder.Optimisation;
import kps.distribution.pathFinder.PathCondition;
import kps.distribution.pathFinder.PathFinder;

@XmlRootElement(name = "DistributionNetwork")
@XmlType(propOrder = {"locations", "routes", "companies"})
public class DistributionNetwork {

	private Map<String, Location> locations = new HashMap<String, Location>();
	private Set<Route> routes = new HashSet<Route>();
	private Map<String, Company> companies = new HashMap<String, Company>();
	private PathFinder pathFinder;

	public DistributionNetwork(){
		for (Location location : LocationRepository.getLocations()){
			locations.put(location.name, location);
		}

		for (Route r : CostRepository.getRoutes(locations)){
			try {
				addRoute(r);
			} catch (InvalidRouteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



	}

	public void addLocation(Location location){
		locations.put(location.getName(), location);
	}

	public void addLocations(Collection<Location> locations){
		for (Location location : locations){
			addLocation(location);
		}
	}

	public Set<Location> getLocationsReachableFrom(String locationName){
		Location location = locations.get(locationName);
		if (location == null)
			throw new InvalidParameterException("Location " + locationName + " could not be found");
		return getLocationsReachableFrom(location);
	}

	public Set<Location> getLocationsReachableFrom(Location location){
		return location.getReachableLocations();
	}

	public Route getRoute(Company company, Location origin, Location destination, TransportType type){
		return routes.stream()
				.filter(r -> r.getCompany().equals(company)
						&& r.getOrigin().equals(origin)
						&& r.getDestination().equals(destination)
						&& r.getType().equals(type))
						.findFirst().orElse(null);
	}

	public void addRoute(Route route) throws InvalidRouteException{
		if (!getLocationsSet().contains(route.getOrigin()))
			throw new InvalidRouteException("Origin location does not exist");
		if (!getLocationsSet().contains(route.getDestination()))
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

	public Set<Location> getLocationsSet(){
		return new HashSet<Location>(this.locations.values());
	}

	public Map<String, Location> getLocations(){
		return locations;
	}

	public void setLocations(Map<String, Location> locations){
		this.locations = locations;
	}

	@XmlElementWrapper(name = "Routes")
	@XmlElement(name = "Route")
	public Set<Route> getRoutes(){
		return this.routes;
	}

	public void setRoutes(Set<Route> routes){
		this.routes = routes;
	}

	public Map<String, Company> getCompanies(){
		return this.companies;
	}

	public void setCompanies(Map<String, Company> comapnies){
		this.companies = comapnies;
	}

	public EventResult processEvent(DistributionNetworkEvent event) {

		if (event instanceof MailDeliveryEvent){
			MailDeliveryEvent mde = (MailDeliveryEvent)event;
			return processMailDeliveryEvent(mde);
		}
		else if (event instanceof CustomerPriceEvent){
			CustomerPriceEvent cpe = (CustomerPriceEvent)event;
			return processCustomerPriceEvent(cpe);
		}

		else if (event instanceof TransportCostEvent){
			TransportCostEvent tce = (TransportCostEvent)event;
			return processTransportCostEvent(tce);
		}

		else if (event instanceof UpdateTableEvent){
			UpdateTableEvent ute = (UpdateTableEvent)event;
			return processUpdateTableEvent(ute);
		}
		
		else if (event instanceof LocationEvent){
			LocationEvent ute = (LocationEvent)event;
			return processLocationEvent(ute);
		}

		else{
			return new InvalidEventResult("Event type '" + event.getClass().getName() + "' not supported");
		}
	}

	private EventResult processUpdateTableEvent(UpdateTableEvent event) {
		return new UpdateTableEventResult();
	}

	private EventResult processTransportCostEvent(TransportCostEvent event) {

		Company company = getOrAddCompany(event.company);
		Location origin = locations.get(event.from);
		Location destination = locations.get(event.to);
		TransportType type = TransportType.fromString(event.type);

		if(event instanceof TransportCostUpdateEvent){
			//TODO: This needs to be looked at - G/L
			//			Route route = getRoute(company, origin, destination, type);
			//			if (route != null){
			//				route.update(event);
			//			}
			CostRepository.update(event.company, event.from, event.to, event.type, event.weightCost, event.volumeCost, event.maxWeight, event.maxVolume, event.duration, event.frequency, event.day);

		}else if(event instanceof TransportCostAddEvent){
			Route route = new Route(origin, destination, company, event.weightCost, event.volumeCost,
					event.volumeCost, event.maxWeight, event.maxVolume, event.frequency, type, event.day);

			routes.add(route);
			CostRepository.add(event.company, event.from, event.to, event.type, event.weightCost, event.volumeCost, event.maxWeight, event.maxVolume, event.duration, event.frequency, event.day);

		}else if(event instanceof TransportCostRemoveEvent){
			Set<Route> routesToRemove = routes.stream()
					.filter(r -> r.getCompany().equals(company)
							&& r.getOrigin().equals(origin)
							&& r.getDestination().equals(destination)
							&& r.getType().equals(type))
							.collect(Collectors.toSet());
			if (routesToRemove.isEmpty()) return new InvalidEventResult("Could not find a matching Route");
			routes.remove(routesToRemove);
			CostRepository.remove(event.company, event.from, event.to, event.type);

		}
		return new TransportCostEventResult();
	}

	private EventResult processCustomerPriceEvent(CustomerPriceEvent event) {

		if(event instanceof CustomerPriceAddEvent){
			PriceRepository.add(event.from, event.to, event.priority, event.weightCost, event.volumeCost);
		}
		else if(event instanceof CustomerPriceUpdateEvent){
			PriceRepository.update(event.from, event.to, event.priority, event.weightCost, event.volumeCost);
		}
		else if(event instanceof CustomerPriceRemoveEvent){
			PriceRepository.remove(event.from, event.to, event.priority);
		}

		return new CustomerPriceEventResult();
	}

	private EventResult processLocationEvent(LocationEvent event) {

		if(event instanceof LocationAddEvent){
			LocationRepository.add(event.location, event.longtitude, event.latitude);
		}
		else if(event instanceof LocationUpdateEvent){
			LocationRepository.update(event.location, event.longtitude, event.latitude);
		}
		else if(event instanceof LocationRemoveEvent){
			LocationRepository.remove(event.location);
		}

		return new LocationEventResult();
	}
	
	private EventResult processMailDeliveryEvent(MailDeliveryEvent event) {
		Mail mail = new Mail(locations.get(event.from), locations.get(event.to),
				event.weight, event.volume, Priority.fromString(event.priority), event.day);
		MailDelivery md = null;
		try {
			md = deliver(mail);
			return new MailDeliveryEventResult(event.day, event.from, event.to, event.weight, event.volume, event.priority, event.price, md.cost, md.time);
		} catch (PathNotFoundException e) {
			return new InvalidEventResult("Could not find a path from origin to destination");
		}
	}
}
