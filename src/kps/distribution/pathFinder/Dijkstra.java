package kps.distribution.pathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import kps.distribution.network.Location;
import kps.distribution.network.Mail;
import kps.distribution.network.MailDelivery;
import kps.distribution.network.Route;

public class Dijkstra implements PathFinder{
	private Mail mail;
	private PriorityQueue<DijkstraNode> fringe = new PriorityQueue<DijkstraNode>();
	private Set<Location> visited = new HashSet<Location>();
	private PathCondition pathCondition;

	public Dijkstra(PathCondition pathCondition){
		this.pathCondition = pathCondition;
	}

	public MailDelivery getPath(Mail mail) throws PathNotFoundException{
		this.mail = mail;
		fringe.add(new DijkstraNode(mail.origin, null, null, 0, 0));

		while (!fringe.isEmpty()) {
			DijkstraNode node = fringe.poll();

			if (node.location == mail.destination)
				return new MailDelivery(mail, node.costToHere, pathTo(node));

			if (visited.contains(node.location))
				continue;

			visited.add(node.location);
			evaluateNeighbors(node);
		}

		throw new PathNotFoundException();
	}

	private List<Route> pathTo(DijkstraNode node) {
		List<Route> path = new ArrayList<Route>();
		while (node.routeToHere != null){
			path.add(node.routeToHere);
			node = node.fromNode;
		}
		Collections.reverse(path);
		return path;
	}

	private void evaluateNeighbors(DijkstraNode node){
		List<Route> routesToCheck = node.location.getRoutesOut().stream()
			.filter(r -> r.canShip(mail) && !visited.contains(r.getDestination()))
			.collect(Collectors.toList());

		for (Route route : routesToCheck) {
			DijkstraNode neighbour = node.plusRoute(route, mail);
			if (pathCondition.accepts(neighbour.costToHere, neighbour.timeToHere));
				fringe.add(neighbour);
		}
	}
}
