package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import kps.distribution.network.Location;

public class LocationRepository {

	private static Connection db = null;

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

	public static Location getCity(String city){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT * FROM cities WHERE \"city_name\"=\""+city+"\"";
			ResultSet result = statement.executeQuery(query);
			Location l = new Location(result.getString(1), result.getDouble(3), result.getDouble(4));
			db.close();
			return l;		
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
				cities.add(new Location(result.getString(1), result.getDouble(3), result.getDouble(4)));		
			}
			db.close();
			return cities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
}