package kps.dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import kps.distributionNetwork.Location;
import kps.distributionNetwork.Mail;
import kps.distributionNetwork.MailDelivery;
import kps.distributionNetwork.PathFinder;
import kps.distributionNetwork.Route;

public class Dijkstra implements PathFinder{
	private Mail mail;
	private PriorityQueue<DijkstraNode> fringe = new PriorityQueue<DijkstraNode>();
	private Set<Location> visited = new HashSet<Location>();
	
	public MailDelivery getPath(Mail mail){
		this.mail = mail;
		
		fringe.add(new DijkstraNode(mail.origin, null, null, 0));

		while (!fringe.isEmpty()) {
			DijkstraNode node = fringe.poll();
			if (node.getLocation() == mail.destination)
				return new MailDelivery(mail, node.costToHere(), pathTo(node));
			if (visited.contains(node.getLocation())) 
				continue;
			visited.add(node.getLocation());
		    evaluateNeighbors(node);
		}
		
		return null; //no path found
	}

	private List<Route> pathTo(DijkstraNode node) {
		List<Route> path = new ArrayList<Route>();
		while (node.routeToHere() != null){
			path.add(0, node.routeToHere());
			node = node.getFromNode();
		}
		return path;
	}

	private void evaluateNeighbors(DijkstraNode node){
		for (Route route : node.getLocation().getRoutesOut()) {
			float cost = node.costToHere() + route.getCost(mail);
			DijkstraNode neighbour = new DijkstraNode(route.getDestination(), node, route, cost);
			fringe.add(neighbour);
		}
	}
}
