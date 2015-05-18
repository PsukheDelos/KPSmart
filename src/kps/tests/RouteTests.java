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
	private Location a = new Location("A", 1, 1);
	private Location b = new Location("B", 1, 1);
	private Company c = new Company("C");
	private Random random = new Random();

	@Test
	public void routeCalculatesCorrectWeightCost(){
		float weightCost = random.nextInt(10);
		float weight = random.nextInt(10);
		Route r = new Route(a, b, c, weightCost, 0, 1000, 1000, 5, 5, TransportType.AIR);
		Mail mail = new Mail(a, b, weight, 0, Priority.DOMESTIC_STANDARD, new Date());
		
		assertTrue(r.getCost(mail) == weight * weightCost);
	}

	@Test
	public void routeCalculatesCorrectVolumeCost(){
		float volumeCost = random.nextInt(10);
		float volume = random.nextInt(10);
		Route r = new Route(a, b, c, 0, volumeCost, 1000, 1000, 5, 5, TransportType.AIR);
		Mail mail = new Mail(a, b, 0, volume, Priority.DOMESTIC_STANDARD, new Date());
		
		assertTrue(r.getCost(mail) == volume * volumeCost);
	}
	
	@Test
	public void combinesWeightAndVolumeCost(){
		float weightCost = random.nextInt(10);
		float weight = random.nextInt(10);
		float volumeCost = random.nextInt(10);
		float volume = random.nextInt(10);
		Route r = new Route(a, b, c, weightCost, volumeCost, 1000, 1000, 5, 5, TransportType.AIR);
		Mail mail = new Mail(a, b, weight, volume, Priority.DOMESTIC_STANDARD, new Date());
		
		assertTrue(r.getCost(mail) == volume * volumeCost + weight * weightCost);
	}
}
