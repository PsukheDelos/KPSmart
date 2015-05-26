package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kps.backend.UserPermissions;
import kps.backend.users.DuplicateUserException;
import kps.backend.users.User;

public class UserRepository {
	
	private static Connection db = null;
	
	// String query = "CREATE TABLE users (ID INT PRIMARY KEY NOT NULL, username VARCHAR (255) NOT NULL, password VARCHAR(255) NOT NULL, permission INT NOT NULL";
	
	public static boolean thereIsAConnectionToTheDatabase(){
		try {
			if (db==null || db.isClosed()==true){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static User authenticateUser(String username, String passwordHash){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT username,permission FROM users WHERE username='" + username + "' AND password='" + passwordHash + "'";
			ResultSet result = statement.executeQuery(query);
			db.close();
			return new User(result.getString(1), UserPermissions.values()[result.getInt(2)]);
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static void registerNewUser(String username, String passwordHash, UserPermissions permissionLevel) throws DuplicateUserException{
		// Assumes that new users WONT have spaces in the username, and the password is already hashed
		if (!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		if (containsUser(username)) throw new DuplicateUserException("Attempting to add duplicate user " + username);
		
		try {
			Statement statement = db.createStatement();
			String query = "INSERT INTO users (ID,username,password,permission) VALUES (NULL, '" + username + "', '" + passwordHash + "', " + permissionLevel.ordinal();
			statement.executeQuery(query);
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean containsUser(String username){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT Count(*) FROM users WHERE username=" + username;
			ResultSet result = statement.executeQuery(query);
			db.close();
			return result.first();
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
}
