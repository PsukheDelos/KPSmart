package kps.distribution.pathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import kps.distribution.exception.PathNotFoundException;
import kps.distribution.network.Location;
import kps.distribution.network.Mail;
import kps.distribution.network.MailDelivery;
import kps.distribution.network.Route;

public class Dijkstra implements PathFinder{
	private Mail mail;
	private PriorityQueue<DijkstraNode> fringe;
	private Set<Location> visited = new HashSet<Location>();
	private PathCondition pathCondition;

	public Dijkstra(PathCondition pathCondition){
		this.pathCondition = pathCondition;
		this.fringe = new PriorityQueue<DijkstraNode>(pathCondition);
	}

	public MailDelivery getPath(Mail mail) throws PathNotFoundException{
		this.mail = mail;
		fringe.add(DijkstraNode.startNode(mail.origin));

		while (!fringe.isEmpty()) {
			DijkstraNode node = fringe.poll();

			if (node.location.equals(mail.destination)){
				return new MailDelivery(mail, node.costToHere, node.timeToHere, pathTo(node));
			}

			if (visited.contains(node.location))
				continue;

			visited.add(node.location);
			evaluateNeighbors(node);
		}

		throw new PathNotFoundException();
	}

	private List<Route> pathTo(DijkstraNode node) {
		List<Route> path = new ArrayList<Route>();
		while (!node.isStartNode()){
			path.add(node.routeToHere);
			node = node.fromNode;
		}
		Collections.reverse(path);
		return path;
	}

	private void evaluateNeighbors(DijkstraNode node){
		List<Route> routesToAdd = node.location.getRoutesOut().stream()
				.filter(r -> r.canShip(mail)
						&& !visited.contains(r.getDestination())
						&& pathCondition.accepts(r))
						.collect(Collectors.toList());

		for (Route route : routesToAdd) {
			DijkstraNode neighbour = node.plusRoute(route, mail);
			fringe.add(neighbour);
		}
	}
}
