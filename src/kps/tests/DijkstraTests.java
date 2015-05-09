package kps.tests;

import static org.junit.Assert.*;

import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import kps.backend.database.KPSDatabase;
import kps.distributionNetwork.Company;
import kps.distributionNetwork.DistributionNetwork;
import kps.distributionNetwork.InvalidRouteException;
import kps.distributionNetwork.Location;
import kps.distributionNetwork.Mail;
import kps.distributionNetwork.MailDelivery;
import kps.distributionNetwork.Route;

import org.junit.Test;

public class DijkstraTests {
	@Test
	public void testSinglePath() throws InvalidRouteException{
		// Act
		Location a = new Location("A");
		Location b = new Location("B");
		Route route = new Route(a, b, new Company("C"), 1, 2, 1000, 1000, 1, 1, "Air");
		Mail mail = new Mail(a, b, 10, 30, "priority", new Date());
		
		DistributionNetwork network = new DistributionNetwork();
		network.addLocation(a);
		network.addLocation(b);
		network.addRoute(route);
		
		// Assert
		MailDelivery delivery = network.deliver(mail);
		assertTrue(delivery.path.contains(route));
		assertTrue(delivery.path.size() == 1);
	}
}
