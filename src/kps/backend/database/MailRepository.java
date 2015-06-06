package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import kps.distribution.network.Location;

public class MailRepository {

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
			model.addColumn("day");
			model.addColumn("origin");
			model.addColumn("destination");
			model.addColumn("type");
			model.addColumn("weight");
			model.addColumn("volume");
			model.addColumn("priority");

			Statement statement = db.createStatement();
			String query = "SELECT * FROM mail";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
			    model.addRow(new Object[] {result.getString(1), result.getString(2), result.getString(3), 
			    		Double.valueOf(result.getString(4)), Double.valueOf(result.getString(5)), result.getString(6)});
			}
			db.close();
			
			return model;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static Boolean add(String day, String origin, String destination, Double weight, Double volume, String priority){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "INSERT INTO mail ([day], [from], [to], [weight], [volume], [priority], [datetime]) VALUES (\""+day+"\",\""+origin+"\",\""+destination+"\","+weight+","+volume+",\""+priority+"\", " + System.currentTimeMillis()/1000 + ")";
			System.out.println(query);
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	
	public static Double getRevenue(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT SUM(price) FROM mail";
			ResultSet result = statement.executeQuery(query);
			Double d = result.getDouble(1);
			db.close();
			return d;		
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static Double getExpenditure(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT SUM(cost) FROM mail";
			ResultSet result = statement.executeQuery(query);
			Double d = result.getDouble(1);
			db.close();
			return d;		
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static Integer getEventCount(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "SELECT COUNT(*) FROM mail";
			System.out.println(query);
			ResultSet result = statement.executeQuery(query);
			System.err.println(result.getInt(1));
			Integer i = result.getInt(1);
			db.close();
			return i;		
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static TableModel getAmountOfMailModel(){
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
			model.addColumn("destination");
			model.addColumn("origin");
			model.addColumn("total volume");
			model.addColumn("total weight");
			model.addColumn("total items");

			Statement statement = db.createStatement();
			
			String query = "select [to], [from], sum(volume), sum(weight), count(*) from mail group by [to],[from]";
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()){
				model.addRow(new Object[] {result.getString(1), result.getString(2), result.getDouble(3), 
			    		result.getDouble(4), result.getInt(5)});
			
			}
			
			db.close();
			
			return model;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	
		
}
