package kps.backend.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class KPSDatabase {

	
	public static final String DATABASE_NAME = "kpsmart.db";	
	
	public static Connection createConnection(){
		
		Connection connection = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
		}catch(Exception ex){ 
			ex.printStackTrace();
		}
		return connection;
	}

}
