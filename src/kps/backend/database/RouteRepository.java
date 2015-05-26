package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class RouteRepository {

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

	public static TableModel getRoutesModel(){
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
			model.addColumn("from");
			model.addColumn("to");
			model.addColumn("method");
			model.addColumn("category");
			Statement statement = db.createStatement();
			String query = "SELECT * FROM route";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
			    model.addRow(new Object[] {result.getString(1), result.getString(2), result.getString(3), result.getString(4)});
			}
			db.close();
			return model;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	
	public static ArrayList<String> getFromCities(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"from\" FROM route order by \"from\" asc";
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
	
	public static ArrayList<String> getToCities(String fromCity){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT distinct \"to\" FROM route where \"from\"=\""+fromCity+"\" order by \"to\" asc";
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
	
	public static Boolean removeRoute(String fromCity, String toCity, String category){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "DELETE FROM route where \"from\"=\""+fromCity+"\" and \"to\"=\""+toCity+"\" and \"category\"=\""+category+"\"";
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}

	
	
	
}
