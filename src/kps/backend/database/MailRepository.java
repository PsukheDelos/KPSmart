package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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

//	public static ArrayList<TransportCostUpdateEvent> getRoutes(){
//		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
//		try {
//			ArrayList<TransportCostUpdateEvent> routes = new ArrayList<TransportCostUpdateEvent>();
//			Statement statement = db.createStatement();
//			String query = "SELECT * FROM cost";
//			ResultSet result = statement.executeQuery(query);
//			while(result.next()){
//			    routes.add(new TransportCostUpdateEvent(result.getString(1), result.getString(3), result.getString(2), result.getString(4), 
//			    		Double.valueOf(result.getString(5)), Double.valueOf(result.getString(6)), 
//			    		Double.valueOf(result.getString(7)), Double.valueOf(result.getString(8)), 
//			    		Double.valueOf(result.getString(9)), Double.valueOf(result.getString(10)), result.getString(11)));
//			}
//			db.close();
//			
//			return routes;
//		} catch (SQLException e) {e.printStackTrace();}
//		return null;
//	}
	
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
	
	
//	public static ArrayList<String> getOrigins(){
//		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
//		try {
//			Statement statement = db.createStatement();
//			String query = "SELECT distinct \"from\" FROM cost order by \"from\" asc";
//			ResultSet result = statement.executeQuery(query);
//			ArrayList<String> origins = new ArrayList<String>();
//			while(result.next()){
//				origins.add(result.getString(1));
//			}
//			db.close();
//			return origins;
//		} catch (SQLException e) {e.printStackTrace();}
//		return null;
//	}
//	
//	public static ArrayList<String> getDestinations(String origin){
//		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
//		try {
//			Statement statement = db.createStatement();
//			String query = "SELECT distinct \"to\" FROM cost where \"from\"=\""+origin+"\" order by \"to\" asc";
//			ResultSet result = statement.executeQuery(query);
//			ArrayList<String> destinations = new ArrayList<String>();
//			while(result.next()){
//				destinations.add(result.getString(1));
//			}
//			db.close();
//			return destinations;
//		} catch (SQLException e) {e.printStackTrace();}
//		return null;
//	}
	
	public static Boolean remove(String company, String origin, String destination, String type){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
			Statement statement = db.createStatement();
			String query = "DELETE FROM cost where \"company\"=\""+company+"\" and \"from\"=\""+origin+"\" and \"to\"=\""+destination+"\" and \"type\"=\""+type+"\"";
			statement.execute(query);
			db.close();
			return true;			
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}

	
	
	
}
