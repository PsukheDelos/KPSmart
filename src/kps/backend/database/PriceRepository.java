package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kps.distribution.network.Location;

public class PriceRepository {

	private static Connection db = null;

	public static boolean thereIsAConnectionToTheDatabase(){
		return db != null;
	}

	public static JTable getPricesTable(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
		    DefaultTableModel model = new DefaultTableModel();
			JTable prices = new JTable(model);
			model.addColumn("from");
			model.addColumn("to");
			model.addColumn("priority");
			model.addColumn("price per gram");
			model.addColumn("price per volume");
			Statement statement = db.createStatement();
			String query = "SELECT * FROM price";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
			    model.addRow(new Object[] {result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getDouble(5)});
			}
			return prices;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static ArrayList<String> getFromCities(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"from\" FROM price order by \"from\" asc";
			ResultSet result = statement.executeQuery(query);
			ArrayList<String> fromCities = new ArrayList<String>();
			while(result.next()){
				fromCities.add(result.getString(1));
			}
			return fromCities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static ArrayList<String> getToCities(String fromCity){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"to\" FROM price where \"from\"=\""+fromCity+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			ArrayList<String> toCities = new ArrayList<String>();
			while(result.next()){
				toCities.add(result.getString(1));
			}
			return toCities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static ArrayList<String> getPriorities(String fromCity, String toCity){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"priority\" FROM price where \"from\"=\""+fromCity+"\" and \"to\"=\""+toCity+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			ArrayList<String> priorities = new ArrayList<String>();
			while(result.next()){
				priorities.add(result.getString(1));
			}
			return priorities;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static Double getPriceWeight(String fromCity, String toCity, String priority){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"price_weight\" FROM price where \"from\"=\""+fromCity+"\" and \"to\"=\""+toCity+"\" and \"priority\"=\""+priority+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			double priceWeight = 0;
			while(result.next()){
				priceWeight = result.getDouble(1);
			}
			return priceWeight;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static Double getPriceVolume(String fromCity, String toCity, String priority){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"price_volume\" FROM price where \"from\"=\""+fromCity+"\" and \"to\"=\""+toCity+"\" and \"priority\"=\""+priority+"\" order by \"to\" asc";
			ResultSet result = statement.executeQuery(query);
			double priceVolume = 0;
			while(result.next()){
				priceVolume = result.getDouble(1);
			}
			return priceVolume;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	
	
}
