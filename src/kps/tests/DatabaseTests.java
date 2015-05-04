package kps.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import kps.backend.database.KPSDatabase;
import kps.backend.users.UserRepository;

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

}
