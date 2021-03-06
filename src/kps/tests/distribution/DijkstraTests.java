package kps.tests.distribution;

import static org.junit.Assert.*;

import java.util.Date;

import kps.distribution.exception.PathNotFoundException;
import kps.distribution.network.Company;
import kps.distribution.network.DistributionNetwork;
import kps.distribution.network.Location;
import kps.distribution.network.Mail;
import kps.distribution.network.MailDelivery;
import kps.distribution.network.Priority;
import kps.distribution.network.Route;
import kps.distribution.network.TransportType;

import org.junit.Test;

public class DijkstraTests {
	@Test
	public void testSinglePath() throws Exception{
		// Act
		Location a = new Location("A",1,1);
		Location b = new Location("B",1,1);
		Route route = new Route(a, b, new Company("C"), 1, 2, 1000, 1000, 1, 1, TransportType.AIR, "Friday");
		Mail mail = new Mail(a, b, 10, 30, Priority.DOMESTIC_STANDARD, "Friday");
		
		DistributionNetwork network = new DistributionNetwork();
		network.addLocation(a);
		network.addLocation(b);
		network.addRoute(route);
		
		// Assert
		MailDelivery delivery = network.deliver(mail);
		assertTrue(delivery.path.contains(route));
		assertTrue(delivery.path.size() == 1);
	}

	@Test
	public void choosesCheapesOfTwoPaths() throws Exception{
		// Act
		Location a = new Location("A",1,1);
		Location b = new Location("B",1,1);
		Route cheapRoute = new Route(a, b, new Company("C"), 1, 2, 1000, 1000, 1, 1, TransportType.AIR, "Friday");
		Route expensiveRoute = new Route(a, b, new Company("D"), 10, 20, 1000, 1000, 1, 1, TransportType.SEA, "Friday");
		Mail mail = new Mail(a, b, 10, 30, Priority.DOMESTIC_STANDARD, "Friday");
		
		DistributionNetwork network = new DistributionNetwork();
		network.addLocation(a);
		network.addLocation(b);
		network.addRoute(cheapRoute);
		network.addRoute(expensiveRoute);
		
		// Assert
		MailDelivery delivery = network.deliver(mail);
		assertTrue(delivery.path.contains(cheapRoute));
		assertTrue(!delivery.path.contains(expensiveRoute));
	}

	@Test
	public void followsPathOfLengthTwo() throws Exception{
		// Act
		Location a = new Location("A",1,1);
		Location b = new Location("B",1,1);
		Location c = new Location("C",1,1);
		Route pathA = new Route(a, b, new Company("D"), 1, 2, 1000, 1000, 1, 1, TransportType.AIR, "Friday");
		Route pathB = new Route(b, c, new Company("E"), 10, 20, 1000, 1000, 1, 1, TransportType.SEA, "Friday");
		Mail mail = new Mail(a, c, 10, 30, Priority.DOMESTIC_STANDARD, "Friday");

		DistributionNetwork network = new DistributionNetwork();
		network.addLocation(a);
		network.addLocation(b);
		network.addLocation(c);
		network.addRoute(pathA);
		network.addRoute(pathB);

		// Assert
		MailDelivery delivery = network.deliver(mail);
		assertTrue(delivery.path.size() == 2);
		assertTrue(delivery.path.contains(pathA));
		assertTrue(delivery.path.contains(pathB));
	}

	@Test(expected=PathNotFoundException.class)
	public void pathNotFoundThrowsException() throws Exception{
		Location a = new Location("A",1,1);
		Location b = new Location("B",1,1);
		Mail mail = new Mail(a, b, 10, 30, Priority.DOMESTIC_STANDARD, "Friday");

		DistributionNetwork network = new DistributionNetwork();
		network.addLocation(a);
		network.addLocation(b);

		network.deliver(mail);
	}
}
