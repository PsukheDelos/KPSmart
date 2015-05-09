package kps.dijkstra;

import kps.distributionNetwork.Location;
import kps.distributionNetwork.Route;

public class DijkstraNode implements Comparable<DijkstraNode>{
	private Location location;
	private DijkstraNode fromNode;
	private Route routeToHere;
	private float costToHere;

	public DijkstraNode(Location location, DijkstraNode fromNode,
						Route routeToHere, float costToHere) {
		this.location = location;
		this.fromNode = fromNode;
		this.routeToHere = routeToHere;
		this.costToHere = costToHere;
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	public DijkstraNode getFromNode(){
		return this.fromNode;
	}
	
	public Route routeToHere(){
		return this.routeToHere;
	}
	
	public float costToHere(){
		return this.costToHere;
	}

	@Override
	public int compareTo(DijkstraNode o) {
		if (o == null) return -1;
		if (this.costToHere < o.costToHere) return -1;
		if (this.costToHere > o.costToHere) return 1;
		return 0;
	}
}
