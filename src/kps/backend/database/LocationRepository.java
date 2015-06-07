package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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

	public static TableModel getModel(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			model.addColumn("location");
			model.addColumn("longitude");
			model.addColumn("latitude");
			Statement statement = db.createStatement();
			String query = "SELECT * FROM locations ORDER BY name";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				model.addRow(new Object[] {result.getString(1), result.getString(2), result.getString(3)});
			}
			db.close();
			return model;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static Location getCity(String city){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT * FROM locations WHERE \"name\"=\""+city+"\"";
			ResultSet result = statement.executeQuery(query);
			Location l = new Location(result.getString(1), result.getDouble(2), result.getDouble(3));
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
			String query = "SELECT * FROM locations ORDER BY name";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				Location l = new Location(result.getString(1), result.getDouble(2), result.getDouble(3));
				System.out.println("L: " + l.name);
				cities.add(l);		
			}
			db.close();
			return cities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static ComboBoxModel<String> getLocationNames(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Vector<String> locations=new Vector<String>();
			Statement statement = db.createStatement();
			String query = "SELECT * FROM locations ORDER BY name";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				locations.add(result.getString(1));		
			}
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(locations);
			db.close();
			return model;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static Boolean remove(String location){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "DELETE FROM locations where [name]=\""+location+"\"";
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}

	public static Boolean add(String location, Double lon, Double lat){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "INSERT INTO locations ([name], [lon], [lat]) VALUES (\""+location+"\","+lon+","+lat+")";
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}

	public static Boolean update(String location, Double lon, Double lat){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "UPDATE locations SET [lon]="+lon+", [lat]="+lat+" WHERE [name]=\""+location+"\"";
			System.err.println(query);

			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}

}