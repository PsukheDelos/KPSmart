package kps.backend.users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kps.backend.UserPermissions;
import kps.backend.database.KPSDatabase;

public class UserRepository {
	
	private static Connection db = null;
	
	// String query = "CREATE TABLE users (ID INT PRIMARY KEY NOT NULL, username VARCHAR (255) NOT NULL, password VARCHAR(255) NOT NULL, permission INT NOT NULL";
	
	
	public static User authenticateUser(String username, String password){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT * FROM users WHERE username=" + username;
			ResultSet set = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void registerNewUser(String username, String password, UserPermissions permissionLevel){
		// Assumes that assing new users WONT have spaces in the username, and the password is already hashed
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		
		Statement statement;
		try {
			statement = db.createStatement();
			String query = "INSERT INTO users (ID,username,password,permission) VALUES (NULL, '" + username + "', '" + password + "', " + permissionLevel.ordinal();
			statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		
		
	}
	
	public static boolean thereIsAConnectionToTheDatabase(){
		return db != null;
	}

}
