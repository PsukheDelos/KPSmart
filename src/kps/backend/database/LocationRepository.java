package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kps.backend.UserPermissions;
import kps.backend.users.DuplicateUserException;
import kps.backend.users.User;

public class LocationRepository {

	private static Connection db = null;

	public static boolean thereIsAConnectionToTheDatabase(){
		return db != null;
	}

	public static Location getCity(String city){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			ArrayList<Location> cities = new ArrayList<Location>();
			Statement statement = db.createStatement();
			String query = "SELECT * FROM cities WHERE city_name=\""+city+"\"";
			ResultSet result = statement.executeQuery(query);
			return new Location(result.getString(1), result.getString(2), result.getDouble(3), result.getDouble(4));		
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static ArrayList<Location> getLocations(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			ArrayList<Location> cities = new ArrayList<Location>();
			Statement statement = db.createStatement();
			String query = "SELECT * FROM cities";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				cities.add(new Location(result.getString(1), result.getString(2), result.getDouble(3), result.getDouble(4)));		
			}
			return cities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
}
