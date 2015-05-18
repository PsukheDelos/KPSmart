package kps.distribution.pathFinder;

import kps.distribution.network.Location;
import kps.distribution.network.Mail;
import kps.distribution.network.Route;

public class DijkstraNode implements PathFinderNode{
	public final Location location;
	public final DijkstraNode fromNode;
	public final Route routeToHere;
	public final double costToHere;
	public final double timeToHere;

	private boolean isStartNode = false;

	public DijkstraNode(Location location, DijkstraNode fromNode,
						Route routeToHere, double costToHere, double timeToHere) {
		this.location = location;
		this.fromNode = fromNode;
		this.routeToHere = routeToHere;
		this.costToHere = costToHere;
		this.timeToHere = timeToHere;
	}

	public DijkstraNode plusRoute(Route route, Mail mail) {
		double cost = costToHere + route.getCost(mail);
		double time = timeToHere + route.getDuration();
		return new DijkstraNode(route.getDestination(), this, route, cost, time);
	}

	public double getCost() {
		return costToHere;
	}

	public double getTime() {
		return timeToHere;
	}

	public boolean isStartNode(){
		return this.isStartNode;
	}

	public static DijkstraNode startNode(Location startLocation){
		DijkstraNode node = new DijkstraNode(startLocation, null, null, 0, 0);
		node.isStartNode = true;
		return node;
	}
}
