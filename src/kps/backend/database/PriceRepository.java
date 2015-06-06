package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class PriceRepository {

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
	
	public static TableModel getPricesModel(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
			};
			model.addColumn("origin");
			model.addColumn("destination");
			model.addColumn("priority");
			model.addColumn("weight cost");
			model.addColumn("volume cost");
			Statement statement = db.createStatement();
			String query = "SELECT * FROM price";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				model.addRow(new Object[] {result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getDouble(5)});
			}
			db.close();
			return model;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static ArrayList<String> getOriginCities(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"from\" FROM price order by \"from\" asc";
			ResultSet result = statement.executeQuery(query);
			ArrayList<String> fromCities = new ArrayList<String>();
			while(result.next()){
				fromCities.add(result.getString(1));
			}
			db.close();
			return fromCities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static ArrayList<String> getDestinationCities(String fromCity){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"to\" FROM price where \"from\"=\""+fromCity+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			ArrayList<String> toCities = new ArrayList<String>();
			while(result.next()){
				toCities.add(result.getString(1));
			}
			db.close();
			return toCities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static ArrayList<String> getPriorities(String origin, String destination){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"priority\" FROM price where \"from\"=\""+origin+"\" and \"to\"=\""+destination+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			ArrayList<String> priorities = new ArrayList<String>();
			while(result.next()){
				priorities.add(result.getString(1));
			}
			db.close();
			return priorities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static Double getWeightCost(String origin, String destination, String priority){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct [weightcost] FROM price where \"from\"=\""+origin+"\" and \"to\"=\""+destination+"\" and \"priority\"=\""+priority+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			double priceWeight = 0;
			while(result.next()){
				priceWeight = result.getDouble(1);
			}
			db.close();
			return priceWeight;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static Double getVolumeCost(String origin, String destination, String priority){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct [volumecost] FROM price where \"from\"=\""+origin+"\" and \"to\"=\""+destination+"\" and \"priority\"=\""+priority+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			double priceVolume = 0;
			while(result.next()){
				priceVolume = result.getDouble(1);
			}
			db.close();
			return priceVolume;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}

	public static Boolean remove(String origin, String destination, String priority){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "DELETE FROM price where \"from\"=\""+origin+"\" and \"to\"=\""+destination+"\" and \"priority\"=\""+priority+"\"";
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	
	public static Boolean add(String origin, String destination, String priority, Double weightcost, Double volumecost){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "INSERT INTO price ([from], [to], [priority], [weightCost], [volumeCost]) VALUES (\""+origin+"\",\""+destination+"\",\""+priority+"\","+weightcost+","+volumecost+")";
			System.out.println(query);
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	
	public static Boolean update(String origin, String destination, String priority, Double weightcost, Double volumecost){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "UPDATE price SET [weightCost]="+weightcost+", [volumeCost]="+volumecost+" WHERE [from]=\""+origin+"\" and [to] = \""+destination+"\" and [priority]=\""+priority+"\"";
			System.out.println(query);
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}



}
