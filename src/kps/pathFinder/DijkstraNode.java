package kps.pathFinder;

import kps.distributionNetwork.Location;
import kps.distributionNetwork.Route;

public class DijkstraNode implements Comparable<DijkstraNode>{
	public final Location location;
	public final DijkstraNode fromNode;
	public final Route routeToHere;
	public final float costToHere;

	public DijkstraNode(Location location, DijkstraNode fromNode,
						Route routeToHere, float costToHere) {
		this.location = location;
		this.fromNode = fromNode;
		this.routeToHere = routeToHere;
		this.costToHere = costToHere;
	}

	@Override
	public int compareTo(DijkstraNode o) {
		if (o == null) return -1;
		if (this.costToHere < o.costToHere) return -1;
		if (this.costToHere > o.costToHere) return 1;
		return 0;
	}
}
