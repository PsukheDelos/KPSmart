package kps.tests.distribution;

import static org.junit.Assert.*;
import kps.distribution.network.Location;
import kps.distribution.network.Route;

import org.junit.Test;

public class LocationTests {
	@Test
	public void addingRouteOutWorks(){
		//set up
		Location location = new Location("name", 10, 10);
		assertEquals(location.getRoutesOut().size(), 0);

		//act
		Route r = new Route(location, null, null, 1, 1, 1, 1, 1, 1, null, null);
		location.addRouteOut(r);

		//test
		assertEquals(location.getRoutesOut().size(), 1);
		assertTrue(location.getRoutesOut().contains(r));
	}
}
