package kps.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import kps.backend.database.KPSDatabase;

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

}
