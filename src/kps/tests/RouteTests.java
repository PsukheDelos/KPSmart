package kps.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import kps.distribution.network.Company;
import kps.distribution.network.Location;
import kps.distribution.network.Mail;
import kps.distribution.network.Priority;
import kps.distribution.network.Route;
import kps.distribution.network.TransportType;

import org.junit.Test;

public class RouteTests {
	private Location a = new Location("A",1,1);
	private Location b = new Location("B",1,1);
	private Company c = new Company("C");
	private Random random = new Random();

	@Test
	public void routeCalculatesCorrectWeightCost(){
		double weightCost = random.nextInt(10);
		double weight = random.nextInt(10);
		Route r = new Route(a, b, c, weightCost, 0, 1000, 1000, 5, 5, TransportType.AIR, "Friday");
		Mail mail = new Mail(a, b, weight, 0, Priority.DOMESTIC_STANDARD, "Friday");
		
		assertTrue(r.getCost(mail) == weight * weightCost);
	}

	@Test
	public void routeCalculatesCorrectVolumeCost(){
		double volumeCost = random.nextInt(10);
		double volume = random.nextInt(10);
		Route r = new Route(a, b, c, 0, volumeCost, 1000, 1000, 5, 5, TransportType.AIR, "Friday");
		Mail mail = new Mail(a, b, 0, volume, Priority.DOMESTIC_STANDARD, "Friday");
		
		assertTrue(r.getCost(mail) == volume * volumeCost);
	}
	
	@Test
	public void combinesWeightAndVolumeCost(){
		double weightCost = random.nextInt(10);
		double weight = random.nextInt(10);
		double volumeCost = random.nextInt(10);
		double volume = random.nextInt(10);
		Route r = new Route(a, b, c, weightCost, volumeCost, 1000, 1000, 5, 5, TransportType.AIR, "Friday");
		Mail mail = new Mail(a, b, weight, volume, Priority.DOMESTIC_STANDARD, "Friday");
		
		assertTrue(r.getCost(mail) == volume * volumeCost + weight * weightCost);
	}
}
