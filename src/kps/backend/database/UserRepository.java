package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
			User u = new User(result.getString(1), UserPermissions.values()[result.getInt(2)]);
			db.close();
			return u;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static void registerNewUser(String username, String passwordHash, UserPermissions permissionLevel) throws DuplicateUserException{
		// Assumes that new users WONT have spaces in the username, and the password is already hashed
		if (!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		
		if (containsUser(username)) throw new DuplicateUserException("Attempting to add duplicate user " + username);
		
		try {
			Statement statement = db.createStatement();
			String query = "INSERT INTO users (ID,username,password,permission) VALUES (NULL,'" + username + "','" + passwordHash + "'," + permissionLevel.ordinal() + ")";
			statement.executeUpdate(query);
			db.close();
		} catch (SQLException e) {
			
		}
	}
	
	public static TableModel getUserModel(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		
		try{
			DefaultTableModel model = new DefaultTableModel(){
				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			model.addColumn("id");
			model.addColumn("username");
			model.addColumn("permission");
			
			Statement statement = db.createStatement();
			String query = "SELECT * FROM users";
			ResultSet result = statement.executeQuery(query);
			while(result.next())
				model.addRow(new Object[] { result.getInt(1), result.getString(2), result.getInt(4) });
			
			db.close();
			return model;
		}catch(SQLException e){ e.printStackTrace(); }
		return null;
	}
	
	public static void removeUser(String username){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {		
			Statement statement = db.createStatement();
			String query = "DELETE FROM users WHERE username='" + username + "'";
			statement.executeUpdate(query);
			db.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	private static boolean containsUser(String username){
		try {
			Statement statement = db.createStatement();
			String query = "SELECT Count(*) FROM users WHERE username='" + username + "'";
			ResultSet result = statement.executeQuery(query);
			int res = result.getInt(1);
			System.out.println(res);
			return res > 0;
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
}
