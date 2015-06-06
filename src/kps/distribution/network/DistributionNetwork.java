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
		for (Route r : CostRepository.getRoutes()){
			if (!locations.containsKey(r.getOrigin().getName())){
				locations.put(r.getOrigin().getName(), r.getOrigin());
			}
			if (!locations.containsKey(r.getDestination().getName())){
				locations.put(r.getDestination().getName(), r.getDestination());
			}
			routes.add(r);
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
		System.out.println("DisributionNetwork: addRoute: " + route.getCompany().name);
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
		System.err.println("DisributionNetwork: deliver()");

		switch (mail.priority){
		case DOMESTIC_AIR:
			System.out.println("deliver(): DOMESTIC_AIR");
		case INTERNATIONAL_AIR:
			System.out.println("deliver(): INTERNATIONAL_AIR");
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
			System.out.println("deliver(): DOMESTIC_STANDARD");
		case INTERNATIONAL_STANDARD:
			System.out.println("deliver(): INTERNATIONAL_STANDARD");
			// for standard shipping, land, sea and air are all okay, just find the cheapest
			PathCondition airLandSea = new PathCondition(
					PathType.AIR_SEA_LAND,
					Optimisation.LOWEST_COST
					);
			System.out.println("pathFinder = new Dijkstra(airLandSea);");
			pathFinder = new Dijkstra(airLandSea);
			System.out.println("return pathfinder.getpath()");
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
			System.out.println("DistributionNetwork: processEvent: CustomerPriceEvent");
			CustomerPriceEvent cpe = (CustomerPriceEvent)event;
			return processCustomerPriceEvent(cpe);
		}

		else if (event instanceof TransportCostEvent){
			System.out.println("DistributionNetwork: processEvent: TransportCostEvent");
			TransportCostEvent tce = (TransportCostEvent)event;
			return processTransportCostEvent(tce);
		}
		
		else if (event instanceof UpdateTableEvent){
			System.out.println("DistributionNetwork: processEvent: UpdateTableEvent");
			UpdateTableEvent ute = (UpdateTableEvent)event;
			return processUpdateTableEvent(ute);
		}

		//		else if (event instanceof TransportDiscontinuedEvent){
		//			TransportDiscontinuedEvent tde = (TransportDiscontinuedEvent)event;
		//			return processTransportDiscontinuedEvent(tde);
		//		}

		else{
			System.out.println("DistributionNetwork: processEvent: InvalidEventResult");
			return new InvalidEventResult("Event type '" + event.getClass().getName() + "' not supported");
		}
	}

	//	private EventResult processTransportDiscontinuedEvent(TransportDiscontinuedEvent event) {
	//		Company company = companies.get(event.company);
	//		Location origin = locations.get(event.from);
	//		Location destination = locations.get(event.to);
	//		TransportType type = TransportType.fromString(event.type);
	//
	//		Set<Route> routesToRemove = routes.stream()
	//				.filter(r -> r.getCompany().equals(company)
	//						&& r.getOrigin().equals(origin)
	//						&& r.getDestination().equals(destination)
	//						&& r.getType().equals(type))
	//						.collect(Collectors.toSet());
	//
	//		if (routesToRemove.isEmpty()) return new InvalidEventResult("Could not find a matching Route");
	//
	//		routes.remove(routesToRemove);
	//		return new DiscontinueEventResult("Route removed successfully");
	//	}

	private EventResult processUpdateTableEvent(UpdateTableEvent event) {
		if(event instanceof UpdateTablePriceEvent){
			System.err.println("processUpdateTableEvent: updatetablepriceevent");
		}
		else if(event instanceof UpdateTableRouteEvent){
			System.err.println("processUpdateTableEvent: updatetablerouteevent");
		}
		else if(event instanceof UpdateTableUserEvent){
			System.err.println("processUpdateTableEvent: updatetableusereevent");
		}
			
		return new UpdateTableEventResult();
	}

	private EventResult processTransportCostEvent(TransportCostEvent event) {
		System.out.println("DistributionNetwork: processTransportCostEvent: " + event.getClass());

		Company company = getOrAddCompany(event.company);
		Location origin = locations.get(event.from);
		Location destination = locations.get(event.to);
		TransportType type = TransportType.fromString(event.type);

		if(event instanceof TransportCostUpdateEvent){
			System.out.println("DistributionNetwork: processTransportCostEvent: TransportCostUpdateEvent");
//			Route route = getRoute(company, origin, destination, type);
//			if (route != null){
//				route.update(event);
//			}
			CostRepository.update(event.company, event.from, event.to, event.type, event.weightCost, event.volumeCost, event.maxWeight, event.maxVolume, event.duration, event.frequency, event.day);

		}else if(event instanceof TransportCostAddEvent){
			System.out.println("DistributionNetwork: processTransportCostEvent: TransportCostAddEvent");
			Route route = new Route(origin, destination, company, event.weightCost, event.volumeCost,
					event.volumeCost, event.maxWeight, event.maxVolume, event.frequency, type, event.day);

			routes.add(route);
			CostRepository.add(event.company, event.from, event.to, event.type, event.weightCost, event.volumeCost, event.maxWeight, event.maxVolume, event.duration, event.frequency, event.day);

		}else if(event instanceof TransportCostRemoveEvent){
			System.out.println("DistributionNetwork: processTransportCostEvent: TransportCostRemoveEvent");
			Set<Route> routesToRemove = routes.stream()
					.filter(r -> r.getCompany().equals(company)
							&& r.getOrigin().equals(origin)
							&& r.getDestination().equals(destination)
							&& r.getType().equals(type))
							.collect(Collectors.toSet());
			System.err.println(event.company + event.from + event.to + event.type);
			if (routesToRemove.isEmpty()) return new InvalidEventResult("Could not find a matching Route");
			routes.remove(routesToRemove);
			CostRepository.remove(event.company, event.from, event.to, event.type);

		}
		return new TransportCostEventResult();
	}

	private EventResult processCustomerPriceEvent(CustomerPriceEvent event) {
		System.out.println("DistributionNetwork: processCustomerPriceEvent: " + event.id);

		if(event instanceof CustomerPriceAddEvent){
			System.out.println("\tCustomerPriceAddEvent");
			PriceRepository.add(event.from, event.to, event.priority, event.weightCost, event.volumeCost);
		}
		else if(event instanceof CustomerPriceUpdateEvent){
			System.out.println("\tCustomerPriceUpdateEvent");
			PriceRepository.update(event.from, event.to, event.priority, event.weightCost, event.volumeCost);
		}
		else if(event instanceof CustomerPriceRemoveEvent){
			System.out.println("\tCustomerPriceRemoveEvent");
			PriceRepository.remove(event.from, event.to, event.priority);
		}

		return new CustomerPriceEventResult();
	}

	private EventResult processMailDeliveryEvent(MailDeliveryEvent event) {
		//		if (!locations.containsKey(event.from)){
		//			locations.put(event.from, new Location(event.from, 100, 100));
		//		}
		//		if (!locations.containsKey(event.to)){
		//			locations.put(event.to, new Location(event.to, 100, 100));
		//		}
		System.err.println("DistributionNetwork: processMailDeliveryEvent()");
		System.out.println("Mail -> f: " + event.from + " t: " + event.to + " " + event.priority + " w: " + event.weight + " v: " + event.volume + " d: " + event.day);
		Mail mail = new Mail(locations.get(event.from), locations.get(event.to),
				event.weight, event.volume, Priority.fromString(event.priority), event.day);
		MailRepository.add(event.day, event.from, event.to, event.weight, event.volume, event.priority);
		try {
			System.out.println("try DeliveryEventResult(deliver(mail))");
			return new DeliveryEventResult(deliver(mail));
		} catch (PathNotFoundException e) {
			return new InvalidEventResult("Could not find a path from origin to destination");
		}
	}
}
