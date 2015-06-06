package kps.tests.distribution;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import kps.distribution.network.DepthFirstSearch;
import kps.distribution.network.Location;
import kps.distribution.network.Route;

public class DepthFirstSearchTests {
	@Test
	public void canFollowARoute(){
		//set up
		Location locA = new Location("locA", 1, 1);
		Location locB = new Location("locB", 1, 1);
		Location locC = new Location("locB", 1, 1);
		Route r = new Route(locA, locB, null, 1, 1, 1, 1, 1, 1, null, null);
		locA.addRouteOut(r);

		//act
		Set<Location> reachable = DepthFirstSearch.getReachableNodes(locA);
		
		//test
		assertEquals(reachable.size(), 2); //this node and the node we have a route to
		assertTrue(reachable.contains(locA));
		assertTrue(reachable.contains(locB));
		assertFalse(reachable.contains(locC));
	}
}
