package kps.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import kps.backend.database.KPSDatabase;
import kps.backend.database.LocationRepository;
import kps.backend.database.Location;

import org.junit.Test;

public class DatabaseTests {
	
	@Test
	public void testDatabaseConnectionIsNotNull(){
		// Act
		Connection db = KPSDatabase.createConnection();
		// Assert
		assertFalse(db == null);
		try { db.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
	
	@Test
	public void testDatabaseConnectionIsOpen(){
		// Act
		Connection db = KPSDatabase.createConnection();
		// Assert
		try {assertFalse(db.isClosed());} catch (SQLException e1) {e1.printStackTrace();}
		try { db.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
	
	@Test
	public void testDatabaseConnectionIsWritable(){
		// Act
		Connection db = KPSDatabase.createConnection();
		// Assert
		try {assertFalse(db.isReadOnly());} catch (SQLException e1) {e1.printStackTrace();}
		try { db.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
	
	@Test
	public void testDatabaseLocations(){
		ArrayList<Location> locations = LocationRepository.getLocations();
		System.out.println("Database.getLocations() Test: ");
		System.out.println("\tThere are " + locations.size() + " cities in the database.");
		System.out.println("\tThe first city is " + locations.get(0).city + ".");
		System.out.println("\tThe last city is " + locations.get(locations.size()-1).city + ".");
	}

}
