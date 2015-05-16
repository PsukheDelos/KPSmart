package kps.distribution.pathFinder;

import kps.distribution.network.Location;
import kps.distribution.network.Mail;
import kps.distribution.network.Route;

public class DijkstraNode implements PathFinderNode, Comparable<DijkstraNode>{
	public final Location location;
	public final DijkstraNode fromNode;
	public final Route routeToHere;
	public final float costToHere;
	public final float timeToHere;

	public DijkstraNode(Location location, DijkstraNode fromNode,
						Route routeToHere, float costToHere, float timeToHere) {
		this.location = location;
		this.fromNode = fromNode;
		this.routeToHere = routeToHere;
		this.costToHere = costToHere;
		this.timeToHere = timeToHere;
	}

	public DijkstraNode plusRoute(Route route, Mail mail) {
		float cost = costToHere + route.getCost(mail);
		float time = timeToHere + route.getDuration();
		return new DijkstraNode(route.getDestination(), this, route, cost, time);
	}

	public int compareTo(DijkstraNode o) {
		if (o == null) return -1;
		if (this.costToHere < o.costToHere) return -1;
		if (this.costToHere > o.costToHere) return 1;
		return 0;
	}

	public float getCost() {
		return costToHere;
	}

	public float getTime() {
		return timeToHere;
	}
}
