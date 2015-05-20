package kps.distribution.network;

import java.util.HashSet;
import java.util.Set;

public class DepthFirstSearch {

	public static Set<Location> getReachableNodes(Location root) {
		Set<Location> nodes = new HashSet<Location>();
		return getReachableNodes(root, nodes);
	}

	public static Set<Location> getReachableNodes(Location node, Set<Location> visited){
		visited.add(node);
		for (Route r : node.getRoutesOut()){
			if (visited.contains(r.getDestination())) continue;
			visited = getReachableNodes(r.getDestination(), visited);
		}
		return visited;
	}

}
